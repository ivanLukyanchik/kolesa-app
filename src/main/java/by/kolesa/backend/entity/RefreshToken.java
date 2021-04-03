package by.kolesa.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "REFRESH_TOKENS")
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String token;

  @Column(name = "CREATED_DATE")
  private Instant createdDate;
}
