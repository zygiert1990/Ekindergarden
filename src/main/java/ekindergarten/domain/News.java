package ekindergarten.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = {"groups"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 100)
    private String title;

    private LocalDate date;

    @Column(length = 1000)
    private String content;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] image;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ChildGroup> groups;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(content);
        hcb.append(title);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof News)) {
            return false;
        }
        News that = (News) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(content, that.content);
        eb.append(title, that.title);
        return eb.isEquals();
    }

}
