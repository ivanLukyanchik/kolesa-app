package by.kolesa.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QUESTIONS")
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String text;

  @Column(name = "LINK_TO_IMG")
  private String linkToImage;

  @Column(name = "TOPIC_ID")
  private Long topicId;

  @OneToOne private Paragraph paragraph;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "QUESTION_ID")
  private List<Answer> answers;
}
