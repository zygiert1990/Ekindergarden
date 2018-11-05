package ekindergarten.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Wither;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class AbsenceRecordDto {
    @Wither
    private final long id;
    private final LocalDate startAbsence;
    private final LocalDate endAbsence;
    private final String reason;
}
