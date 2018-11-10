package ekindergarten.model.forum.response;

import ekindergarten.domain.forum.Topic;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetCommentsResonse {
    private final Topic topic;
    List<CommentDto> comments;
}
