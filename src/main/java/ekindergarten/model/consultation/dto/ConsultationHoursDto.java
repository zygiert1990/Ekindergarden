package ekindergarten.model.consultation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultationHoursDto {
    private final int hour;
    private final int minutes;

}
