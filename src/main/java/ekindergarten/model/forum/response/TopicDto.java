package ekindergarten.model.forum.response;

import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TopicDto {
    private long id;
    private String title;
    private String content;
    private LocalDate creationDate;
    private UserDto author;
}
