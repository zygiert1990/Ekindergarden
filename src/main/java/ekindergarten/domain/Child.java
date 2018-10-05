package ekindergarten.domain;

import ekindergarten.validation.ValidName;
import ekindergarten.validation.ValidSurnameAndCity;
import lombok.*;
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
@NoArgsConstructor
@ToString(exclude = "users")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ValidName
    @NotNull
    @Size(max = 45)
    @Column(length = 45, nullable = false)
    private String name;

    @ValidSurnameAndCity
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

    @ManyToMany(mappedBy = "children")
    private Set<User> users;

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

    public static Builder builder() {
        return new Child.Builder();
    }

    public static class Builder {
        private Child instance;

        private Builder() {
            instance = new Child();
        }

        public Builder withName(String name) {
            instance.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            instance.surname = surname;
            return this;
        }

        public Builder withPesel(String pesel) {
            instance.pesel = pesel;
            return this;
        }

        public Child build() {
            return instance;
        }
    }
}
