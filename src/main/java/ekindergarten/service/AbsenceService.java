package ekindergarten.service;

import com.google.common.collect.Lists;
import ekindergarten.domain.Absence;
import ekindergarten.domain.Child;
import ekindergarten.model.AbsenceRecordDto;
import ekindergarten.repositories.AbsenceRepository;
import ekindergarten.repositories.ChildRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AbsenceService {

    private final ChildRepository childRepository;
    private final AbsenceRepository absenceRepository;

    public AbsenceService(ChildRepository childRepository, AbsenceRepository absenceRepository) {
        this.childRepository = childRepository;
        this.absenceRepository = absenceRepository;
    }

    public List<Absence> addAbsence(AbsenceRecordDto absenceRecordDto, long id) throws RuntimeException {
        List<LocalDate> daysList = new ArrayList<>();
        Child child = childRepository.findById(id);
        LocalDate startAbsence = absenceRecordDto.getStartAbsence();
        LocalDate endAbsence = absenceRecordDto.getEndAbsence();

        if (startAbsence.isAfter(endAbsence))
            throw new RuntimeException("data końca nie może być przed datą początku");

        daysList.add(startAbsence);

        while (startAbsence.isBefore(endAbsence)) {
            startAbsence = startAbsence.plusDays(1);
            daysList.add(startAbsence);
        }

        List<Absence> absenceList = daysList.stream()
                .filter(date -> !date.getDayOfWeek().equals(DayOfWeek.SATURDAY))
                .filter(date -> !date.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                .map(date -> absenceRepository.save(
                        Absence.builder()
                                .absenceDate(date)
                                .reason(absenceRecordDto.getReason())
                                .child(child)
                                .build()
                        )
                )
                .collect(Collectors.toList());

        if (absenceList.size() == 0)
            throw new RuntimeException("Podane daty są wolne od zajęć");
        child.getPayment().setBalance(
                child.getPayment().getBalance().subtract(new BigDecimal(absenceList.size() * 10)));
        childRepository.flush();
        return absenceList;
    }

    public void updateAbsence(long[] ids, String reason) {
        for (long id : ids) {
            absenceRepository.findById(id).setReason(reason);
        }
        absenceRepository.flush();
    }

    public void deleteAbsence(long[] ids) {
        Child child = absenceRepository.findById(ids[0]).getChild();
        for (long id : ids) {
            absenceRepository.deleteById(id);
        }
        child.getPayment().setBalance(
                child.getPayment().getBalance().add(new BigDecimal(ids.length * 10)));
        childRepository.flush();
    }

    public List<Absence> getChildAbsences(long id) {
        Set<Absence> absences = childRepository.findById(id).getAbsences();
        if (absences == null)
            throw new RuntimeException("To dziecko nie posiada żadnych nieobecności");
        return Lists.newArrayList(absences);
    }

}
