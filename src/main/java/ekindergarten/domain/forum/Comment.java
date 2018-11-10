package ekindergarten.domain.forum;

import ekindergarten.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private String content;

    @ManyToOne (fetch = FetchType.EAGER)
    private User author;

    @ManyToOne (fetch = FetchType.EAGER)
    private Topic topic;
}
