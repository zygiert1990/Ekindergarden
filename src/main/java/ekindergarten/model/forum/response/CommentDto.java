package ekindergarten.model.forum.response;

import ekindergarten.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class CommentDto {
    private long id;
    private LocalDate creationDate;
    private String content;
    private UserDto author;


}
