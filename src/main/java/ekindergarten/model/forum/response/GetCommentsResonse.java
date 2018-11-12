package ekindergarten.model.forum.response;

import ekindergarten.domain.forum.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class GetCommentsResonse {
    private final TopicDto topic;
    private final List<CommentDto> comments;
}
