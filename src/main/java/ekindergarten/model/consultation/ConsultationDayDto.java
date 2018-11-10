package ekindergarten.model.consultation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ConsultationDayDto {
    private final long id;
    private final LocalDate consultationDay;
    private final List<ConsultationHoursDto> consultationHours;
}
