package ekindergardenserver.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Child {
    @Id
    @GeneratedValue
    private long id;
    @Column(length = 45, nullable = false)
    private String name;
    @Column(length = 45, nullable = false)
    private String surname;
    @Column(length = 11, nullable = false, unique = true)
    private String pesel;
    @ManyToMany(mappedBy = "children")
    private Set<User> users;

    private Child() {
    }

    public static class Builder {
        private Child instance;

        public Builder() {
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
