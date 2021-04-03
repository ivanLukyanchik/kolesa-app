package by.kolesa.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CONTROLS")
public class Control {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "control")
  private List<UserAnswer> userAnswers;

  @Column(name = "DURATION")
  private int durationInSeconds;

  @Column(name = "USER_ID")
  private Long userId;

  public void addUserAnswer(UserAnswer userAnswer) {
    this.userAnswers.add(userAnswer);
    userAnswer.setControl(this);
  }
}
