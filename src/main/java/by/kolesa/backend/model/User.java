package by.kolesa.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z]\\w{3,15}$", message = "4-16 word characters for username")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Minimum eight characters, at least one letter and one number for password")
    private String password;

    @Email
    private String email;

    @Column(name = "PHONE_NUMBER")
    @Pattern(regexp = "(\\d{7})|(\\d{13})", message = "Please check the format of phone number : 7 or 13 digits")
    private String phoneNumber;

    @Column(name = "CREATED_DATE")
    private Instant createdDate;

    private boolean enabled;

    @Column(name = "REGISTERED_BY_EMAIL")
    private boolean registeredByEmail;

}
