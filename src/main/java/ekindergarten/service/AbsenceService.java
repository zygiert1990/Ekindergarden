package ekindergarten.service;

import ekindergarten.domain.Absence;
import ekindergarten.domain.Child;
import ekindergarten.model.AbsenceRecordDto;
import ekindergarten.repositories.AbsenceRepository;
import org.assertj.core.util.Arrays;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final PaymentService paymentService;

    public AbsenceService(AbsenceRepository absenceRepository, PaymentService paymentService) {
        this.absenceRepository = absenceRepository;
        this.paymentService = paymentService;
    }

    public void deleteAbsence(Long[] ids, long childId) {
        Integer deletedRows = absenceRepository.deleteById(Arrays.nonNullElementsIn(ids), LocalDate.now());
        paymentService.updateChildBalance(childId, deletedRows);
    }

    public List<Absence> getChildAbsences(long id) { return absenceRepository.findByChildId(id); }

    public void addOrUpdateAbsence(List<AbsenceRecordDto> absenceRecordDto, long childId) {
        List<Absence> absences = absenceRepository.saveAll(absenceRecordDto
                .stream()
                .filter(absence -> absence.getAbsenceDate().isAfter(LocalDate.now()))
                .filter(absence -> absence.getAbsenceDate().getDayOfWeek() != DayOfWeek.SATURDAY)
                .filter(absence -> absence.getAbsenceDate().getDayOfWeek() != DayOfWeek.SUNDAY)
                .map(absence -> new Absence(
                        absence.getId(),
                        absence.getAbsenceDate(),
                        absence.getReason(),
                        Child
                                .builder()
                                .id(childId)
                                .build()
                ))
                .collect(Collectors.toList()));

        paymentService.updateChildBalance(childId , -absences.size());
    }
}
