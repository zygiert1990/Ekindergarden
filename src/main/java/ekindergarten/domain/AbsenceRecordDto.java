package ekindergarten.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbsenceRecordDto {
    private final long id;
    private final long absenceDate;
    private final String reason;
}
