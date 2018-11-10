package ekindergarten.domain.consultation;

import ekindergarten.domain.Child;
import ekindergarten.domain.consultation.Consultation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ConsultationHours {

    @Id
    @GeneratedValue
    private long id;

    private int hour;

    private int min;

    @JoinColumn (name = "child_id")
    @OneToOne
    private Child child;

    @JoinColumn (name = "consultation_id")
    @ManyToOne
    private Consultation consultation;
}
