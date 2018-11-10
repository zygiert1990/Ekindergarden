package ekindergarten.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.User;
import ekindergarten.domain.consultation.Consultation;
import ekindergarten.domain.consultation.ConsultationHours;
import ekindergarten.model.consultation.ConsultationDayDto;
import ekindergarten.model.consultation.ConsultationHoursDto;
import ekindergarten.model.consultation.ConsultationsDto;
import ekindergarten.model.UserDto;
import ekindergarten.model.consultation.request.BookConsultationDto;
import ekindergarten.model.consultation.request.CreateConsultationDto;
import ekindergarten.repositories.ConsultationHoursRepository;
import ekindergarten.repositories.ConsultationRepository;
import ekindergarten.utils.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationHoursRepository consultationHoursRepository;

    public ConsultationService(
            ConsultationRepository consultationRepository,
            ConsultationHoursRepository consultationHoursRepository) {
        this.consultationRepository = consultationRepository;
        this.consultationHoursRepository = consultationHoursRepository;
    }

    public List<ConsultationsDto> getAvailableConsultations() {
        List<Consultation> consultations = consultationRepository.findByDayAfter(LocalDate.now());
        List<ConsultationsDto> consultationsDtos = new ArrayList<>();
        Map<UserDto, List<ConsultationsDto>> consultationDays = consultations
                .stream()
                .filter(i -> !i.getConsultationHours().isEmpty())
                .map(i -> ConsultationsDto
                        .builder()
                        .user(UserDto
                                .builder()
                                .name(i.getTeacher().getName())
                                .surname(i.getTeacher().getSurname())
                                .id(i.getTeacher().getId())
                                .build())
                        .consultationDay(
                                Arrays.asList(new ConsultationDayDto(
                                        i.getId(),
                                        i.getDay(),
                                        i.getConsultationHours()
                                                .stream()
                                                .filter(h -> h.getChild() == null)
                                                .map(h -> new ConsultationHoursDto(h.getHour(), h.getMin()))
                                                .collect(Collectors.toList()))))
                        .build())
                .collect(Collectors.groupingBy(ConsultationsDto::getUser, Collectors.toList()));

        consultationDays
                .forEach((user, days) ->
                        consultationsDtos.add(
                                new ConsultationsDto(user, days
                                        .stream()
                                        .map(ConsultationsDto::getConsultationDay)
                                        .flatMap(Collection::stream)
                                        .collect(Collectors.toList()))));

        return consultationsDtos;
    }

    public void deleteById(long id) {
        consultationRepository.deleteByIdAndTeacher(
                id,
                User
                        .builder()
                        .id(CurrentUserProvider.provideUserId())
                        .build()
        );
    }

    public void addConsultation(CreateConsultationDto consultationDto) {
        LocalDateTime start = consultationDto.getStart();
        LocalDateTime end = consultationDto.getEnd();
        Consultation consultation = Consultation
                .builder()
                .day(start.toLocalDate())
                .teacher(User.builder().id(CurrentUserProvider.provideUserId()).build()).build();

        consultation.setConsultationHours(produceConsultationHours(start, end, consultation));

        consultationRepository.save(consultation);
    }

    public void bookConsultation(BookConsultationDto consultationDto) {
        consultationHoursRepository
                .bookConsultation(consultationDto.getChildId(), consultationDto.getConsultationId(),
                        consultationDto.getHour(), consultationDto.getMin());
    }

    public void deleteConsultation(BookConsultationDto consultationDto) {
        consultationHoursRepository
                .bookConsultation(null, consultationDto.getConsultationId(),
                        consultationDto.getHour(), consultationDto.getMin());
    }

    public List<ConsultationHours> getChildConsultations(long childId) {
        return consultationHoursRepository.findByChild(Child.builder().id(childId).build());
    }

    private List<ConsultationHours> produceConsultationHours(LocalDateTime start, LocalDateTime end, Consultation consultation) {
        List<ConsultationHours> consultationHours = new ArrayList<>();

        while (start.isBefore(end) || start.isEqual(end)) {
            consultationHours.add(ConsultationHours
                    .builder()
                    .consultation(consultation)
                    .hour(start.getHour())
                    .min(start.getMinute())
                    .build());
            start = start.plusMinutes(20);
        }

        return consultationHours;
    }


}
