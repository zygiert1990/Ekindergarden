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
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"children", "role"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String surname;

    @Column(length = 9, nullable = false, unique = true)
    private String civilId;

    @Column(length = 45, unique = true)
    private String email;

    @Column(length = 9, unique = true)
    private String phoneNumber;

    private String password;

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Child> children;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(civilId);
        hcb.append(role);
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
        User that = (User) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(civilId, that.civilId);
        eb.append(role, that.role);
        return eb.isEquals();
    }
}