package ekindergarten.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
public class RemarkDto {
    private final long id;
    private final boolean isPositive;
    private final String author;
    private final String comment;
    @Wither
    private final boolean isRead;
    private final long date;
}
