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
public class ProgressCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20)
    private String progressCategory;

    @OneToMany(mappedBy = "progressCategory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Progress> progress;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(progressCategory);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProgressCategory)) {
            return false;
        }
        ProgressCategory that = (ProgressCategory) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(progressCategory, that.progressCategory);
        return eb.isEquals();
    }

}

