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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PARAGRAPHS")
public class Paragraph {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "CHAPTER_ID")
  private Chapter chapter;

  @Column(name = "LINK_TO_IMG")
  private String linkToImage;

  @OneToMany
  @JoinColumn(name = "PARAGRAPH_ID")
  private List<Article> articles;
}
