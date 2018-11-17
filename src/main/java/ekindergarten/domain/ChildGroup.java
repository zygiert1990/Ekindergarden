package ekindergarten.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@ToString(exclude = {"children", "news"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 15)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "childGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Child> children;

    @JsonIgnore
    @ManyToMany(mappedBy = "groups", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<News> news;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(name);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChildGroup)) {
            return false;
        }
        ChildGroup that = (ChildGroup) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(name, that.name);
        return eb.isEquals();
    }

}
