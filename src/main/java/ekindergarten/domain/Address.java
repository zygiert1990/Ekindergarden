package ekindergarten.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 45)
    private String city;
    @Column(nullable = false, length = 6)
    private String zipCode;
    @Column(nullable = false, length = 45)
    private String street;
    @Column(nullable = false, length = 5)
    private String homeNumber;
    @Column(length = 5)
    private String flatNumber;
    @OneToMany(mappedBy = "address")
    private Set<User> users;

    private Address() {
    }

    public static class Builder {
        private Address instance;

        public Builder() {
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
