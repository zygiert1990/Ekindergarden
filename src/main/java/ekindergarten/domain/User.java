package ekindergarten.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"address", "children", "role"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 45, nullable = false)
    private String name;
    @Column(length = 45, nullable = false)
    private String surname;
    @Column(length = 9, nullable = false, unique = true)
    private String civilId;
    @Column(length = 45, nullable = false, unique = true)
    private String email;
    @Column(length = 9, nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @ManyToMany
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

    public static class Builder {
        private User instance;

        public Builder() {
            instance = new User();
        }

        public Builder withName(String name) {
            instance.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            instance.surname = surname;
            return this;
        }

        public Builder withCivilId(String civilId) {
            instance.civilId = civilId;
            return this;
        }

        public Builder withEmail(String email) {
            instance.email = email;
            return this;
        }

        public Builder withPhoneNumber(String phoneNumber) {
            instance.phoneNumber = phoneNumber;
            return this;
        }

        public Builder withPassword(String password) {
            instance.password = password;
            return this;
        }

        public Builder withRole(Role role) {
            instance.role = role;
            return this;
        }

        public User build() {
            return instance;
        }
    }
}