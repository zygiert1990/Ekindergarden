package ekindergarten.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AbsenceRecordDto {
    @Wither
    private final long id;
    private final LocalDate absenceDate;
    private final String reason;
}
