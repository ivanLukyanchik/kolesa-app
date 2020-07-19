package by.kolesa.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CONTROLS")
public class Control {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name = "CONTROL_ID")
    private List<UserAnswer> userAnswers;

    @Column(name = "DURATION")
    private int durationInSeconds;

}
