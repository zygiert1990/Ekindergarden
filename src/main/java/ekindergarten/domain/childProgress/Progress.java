package ekindergarten.domain.childProgress;

import ekindergarten.domain.Child;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "progress_category_id")
    private ProgressCategory progressCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "progress_task_id")
    private ProgressTask progressTask;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "progress_grade_id")
    private ProgressGrade progressGrade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Progress)) return false;
        return Long.valueOf(id) != null && Long.valueOf(id).equals(((Progress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
