package ekindergardenserver.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue
    private long id;
    private String roleName;
    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Role(){}

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
