package ekindergarten.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users", "trustedPeople", "remarks", "payment", "absences"})
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 45)
    @Column(length = 45, nullable = false)
    private String name;

    @Size(max = 45)
    @Column(length = 45, nullable = false)
    private String surname;

    @Column(length = 11, nullable = false, unique = true)
    private String pesel;

    @Column(nullable = false)
    private boolean isActive;

    private String additionalInfo;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<TrustedPerson> trustedPeople;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "child")
    private Payment payment;

    @JsonIgnore
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Remark> remarks;

    @JsonIgnore
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Absence> absences;

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