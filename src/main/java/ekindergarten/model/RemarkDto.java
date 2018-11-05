package ekindergarten.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Wither;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class RemarkDto {
    private final long id;
    private final boolean isPositive;
    private final String author;
    private final String comment;
    private final String subject;
    @Wither
    private final boolean isRead;
    private final LocalDate date;
}
