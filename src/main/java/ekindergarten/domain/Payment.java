package ekindergarten.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "child")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal balance;

    @JsonIgnore
    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private Child child;
}
