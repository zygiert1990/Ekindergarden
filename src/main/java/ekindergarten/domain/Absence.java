package ekindergarten.domain;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"child"})
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate absenceDate;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(child);
        hcb.append(absenceDate);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Absence)) {
            return false;
        }
        Absence that = (Absence) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(child, that.child);
        eb.append(absenceDate, that.absenceDate);
        return eb.isEquals();
    }

}
