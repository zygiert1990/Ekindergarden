package ekindergarten.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceRecordDto {
    @Wither
    private long id;
    private LocalDate absenceDate;
    private String reason;

}
