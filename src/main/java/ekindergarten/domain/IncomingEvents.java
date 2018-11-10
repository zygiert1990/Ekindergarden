package ekindergarten.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Data
@Builder
public class IncomingEvents {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String comment;

    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    private Child child;
}
