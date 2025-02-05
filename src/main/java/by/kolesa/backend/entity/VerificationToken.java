package by.kolesa.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VERIFICATION_TOKENS")
public class VerificationToken {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String token;

  @OneToOne(fetch = LAZY)
  private User user;

  @Column(name = "EXPIRY_DATE")
  private Instant expiryDate;
}
