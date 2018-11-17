package ekindergarten.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MessageDto implements Comparable<MessageDto> {
    private final long id;
    private final String title;
    private final String author;
    private final String content;
    private final Long date;
    private final byte[] image;

    @Override
    public int compareTo(MessageDto o) { return date.compareTo(o.getDate()); }
}
