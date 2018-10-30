package ekindergarten.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ekindergarten.validation.ValidCivilId;
import ekindergarten.validation.ValidName;
import ekindergarten.validation.ValidSurname;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "children")
public class TrustedPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ValidName
    @Column(length = 45, nullable = false)
    private String name;

    @NotNull
    @ValidSurname
    @Column(length = 45, nullable = false)
    private String surname;

    @NotNull
    @ValidCivilId
    @Column(length = 9, nullable = false, unique = true)
    private String civilId;

    @NotNull
    @Pattern(regexp = "\\d{9}", message = "Numer telefonu musi zawierać dziewięć cyfr")
    @Column(length = 9, nullable = false, unique = true)
    private String phoneNumber;

    @JsonIgnore
    @ManyToMany(mappedBy = "trustedPeople", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
