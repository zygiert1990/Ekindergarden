package ekindergarten.domain.childProgress;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProgressGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 10)
    private String progressGrade;

    @OneToMany(mappedBy = "progressGrade", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Progress> progress;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(progressGrade);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProgressGrade)) {
            return false;
        }
        ProgressGrade that = (ProgressGrade) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(progressGrade, that.progressGrade);
        return eb.isEquals();
    }

}
