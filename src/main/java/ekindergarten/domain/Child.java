package ekindergarten.domain;

import ekindergarten.validation.ValidName;
import ekindergarten.validation.ValidSurname;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@ToString(exclude = {"users", "trustedPeople"})
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ValidName
    @NotNull
    @Size(max = 45)
    @Column(length = 45, nullable = false)
    private String name;

    @ValidSurname
    @NotNull
    @Size(max = 45)
    @Column(length = 45, nullable = false)
    private String surname;

    @Pattern(regexp = "\\d{11}")
    @NotNull
    @Column(length = 11, nullable = false, unique = true)
    private String pesel;

    @Column(nullable = false)
    private boolean isActive;

    private String additionalInfo;

    @ManyToMany(mappedBy = "children")
    private Set<User> users;

    @ManyToMany(mappedBy = "children")
    private Set<TrustedPerson> trustedPeople;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(pesel);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Child)) {
            return false;
        }
        Child that = (Child) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(pesel, that.pesel);
        return eb.isEquals();
    }
}