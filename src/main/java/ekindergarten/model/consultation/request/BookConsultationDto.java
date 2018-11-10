package ekindergarten.model.consultation.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class BookConsultationDto {
    @NotNull
    private final long consultationId;
    @NotNull
    private final long childId;
    @NotNull
    private final int hour;
    @NotNull
    private final int min;
}
