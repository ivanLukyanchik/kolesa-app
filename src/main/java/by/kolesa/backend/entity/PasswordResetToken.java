package by.kolesa.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PASSWORD_RESET_TOKENS")
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String token;

  @OneToOne(fetch = LAZY)
  private User user;

  @Column(name = "EXPIRY_DATE")
  private Instant expiryDate;
}
