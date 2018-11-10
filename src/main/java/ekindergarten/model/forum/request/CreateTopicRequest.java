package ekindergarten.model.forum.request;

import lombok.Data;

@Data
public class CreateTopicRequest {
    private long id;
    private String title;
    private String content;
}
