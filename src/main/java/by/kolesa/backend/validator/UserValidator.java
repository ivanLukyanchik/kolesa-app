package by.kolesa.backend.validator;

import by.kolesa.backend.entity.User;
import by.kolesa.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

  private final UserRepository userRepository;

  private List<String> errorMessages;

  @Override
  public boolean isValid(Object target) {
    User user = (User) target;
    return validateUser(user);
  }

  private boolean validateUser(User user) {
    errorMessages = new ArrayList<>();
    errorMessages.add(validateUsernameUnique(user));
    errorMessages.add(validateEmailUnique(user));
    errorMessages.add(validatePhoneNumberUnique(user));
    errorMessages.removeIf(Objects::isNull);
    return errorMessages.isEmpty();
  }

  private String validateUsernameUnique(User user) {
    Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
    if (userOptional.isPresent()) {
      return "User with username " + user.getUsername() + " already exists";
    }
    return null;
  }

  private String validateEmailUnique(User user) {
    if (user.isRegisteredByEmail()) {
      Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
      if (userOptional.isPresent()) {
        return "User with email " + user.getEmail() + " already exists";
      }
      return null;
    }
    return null;
  }

  private String validatePhoneNumberUnique(User user) {
    if (!user.isRegisteredByEmail()) {
      Optional<User> userOptional = userRepository.findByPhoneNumber(user.getPhoneNumber());
      if (userOptional.isPresent()) {
        return "User with phone number " + user.getPhoneNumber() + " already exists";
      }
      return null;
    }
    return null;
  }

  public String parseErrorMessages() {
    StringBuilder sb = new StringBuilder();
    for (String errorMessage : errorMessages) {
      sb.append(errorMessage);
      sb.append(System.lineSeparator());
    }
    return sb.substring(0, sb.length() - System.lineSeparator().length());
  }
}
