package ekindergarten.domain;

import ekindergarten.validation.ValidResidence;
import ekindergarten.validation.ValidSurnameAndCity;
import lombok.*;

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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ValidSurnameAndCity(message = "Invalid city format")
    @Size(max = 45)
    @Column(nullable = false, length = 45)
    private String city;

    @NotNull
    @Pattern(regexp = "\\d{2}-\\d{3}")
    @Column(nullable = false, length = 6)
    private String zipCode;

    @NotNull
    @Size(max = 45)
    @Column(nullable = false, length = 45)
    private String street;

    @NotNull
    @Size(max = 5)
    @ValidResidence(message = "Invalid home number format")
    @Column(nullable = false, length = 5)
    private String homeNumber;

    @Size(max = 5)
    @ValidResidence(message = "Invalid flat number format")
    @Column(length = 5)
    private String flatNumber;

    @OneToMany(mappedBy = "address")
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Long.valueOf(id).equals(address.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public static Builder builder() {
        return new Address.Builder();
    }

    public static class Builder {
        private Address instance;

        private Builder() {
            instance = new Address();
        }

        public Builder withCity(String city) {
            instance.city = city;
            return this;
        }

        public Builder withZipCode(String zipCode) {
            instance.zipCode = zipCode;
            return this;
        }

        public Builder withStreet(String street) {
            instance.street = street;
            return this;
        }

        public Builder withHomeNumber(String homeNumber) {
            instance.homeNumber = homeNumber;
            return this;
        }

        public Builder withFlatNumber(String flatNumber) {
            instance.flatNumber = flatNumber;
            return this;
        }

        public Address build() {
            return instance;
        }
    }
}
