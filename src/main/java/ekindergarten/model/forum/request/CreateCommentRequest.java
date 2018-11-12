package ekindergarten.model.forum.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateCommentRequest {
    private long topicId;
    private String content;
}
