package ekindergarten.domain.consultation;

import ekindergarten.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Consultation {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @OneToOne
    @JoinColumn (name = "teacher_id")
    private User teacher;

    @NotNull
    private LocalDate day;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    private List<ConsultationHours> consultationHours;

}
