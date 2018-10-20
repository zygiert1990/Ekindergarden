package ekindergarten.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MessageDto implements Comparable<MessageDto> {
    private final long id;
    private final String title;
    private final String author;
    private final String content;
    private final Long date;

    @Override
    public int compareTo(MessageDto o) { return date.compareTo(o.getDate()); }
}
