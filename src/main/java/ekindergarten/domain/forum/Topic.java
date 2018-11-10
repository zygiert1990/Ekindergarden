package ekindergarten.domain.forum;

import ekindergarten.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String title;

    @NotNull
    @Size(min = 3, max = 200)
    private String content;

    @NotNull
    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @OneToMany (mappedBy = "topic" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comment;

}
