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
public class ProgressTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String progressTask;

    @OneToMany(mappedBy = "progressTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Progress> progress;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(progressTask);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProgressTask)) {
            return false;
        }
        ProgressTask that = (ProgressTask) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(progressTask, that.progressTask);
        return eb.isEquals();
    }

}
