package ekindergarten.domain;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = "children")
public class TrustedPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String surname;

    @Column(length = 9, nullable = false, unique = true)
    private String civilId;

    @Column(length = 9, nullable = false, unique = true)
    private String phoneNumber;

    @ManyToMany
    private Set<Child> children;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(civilId);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        TrustedPerson that = (TrustedPerson) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(civilId, that.civilId);
        return eb.isEquals();
    }
}
