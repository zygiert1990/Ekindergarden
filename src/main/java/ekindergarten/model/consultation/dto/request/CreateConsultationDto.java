package ekindergarten.model.consultation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CreateConsultationDto {
    private final LocalDateTime start;
    private final LocalDateTime end;

}
