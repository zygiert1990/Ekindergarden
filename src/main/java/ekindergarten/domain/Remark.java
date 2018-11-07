package ekindergarten.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ekindergarten.model.RemarkDto;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"child", "user"})
public class Remark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean isRead;

    private boolean isPositive;

    private LocalDate date;

    private String comment;

    private String subject;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(user);
        hcb.append(child);
        hcb.append(date);
        hcb.append(comment);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Remark)) {
            return false;
        }
        Remark that = (Remark) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(child, that.child);
        eb.append(user, that.user);
        eb.append(date, that.date);
        eb.append(comment, that.comment);
        return eb.isEquals();
    }

    public static RemarkDto mapToRemarkDto(Remark remark) {
        return RemarkDto.builder()
                .id(remark.getId())
                .isPositive(remark.isPositive())
                .isRead(remark.isRead())
                .date(remark.getDate())
                .comment(remark.getComment())
                .author(remark.getUser().getName() + " " + remark.getUser().getSurname())
                .build();
    }
}
