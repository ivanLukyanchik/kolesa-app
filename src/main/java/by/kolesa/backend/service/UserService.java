package by.kolesa.backend.service;

import by.kolesa.backend.exception.UserNotFoundException;
import by.kolesa.backend.exception.UserNotLoggedInException;
import by.kolesa.backend.model.User;
import by.kolesa.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @SneakyThrows
  public User getUser(String username) {
    return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
  }

  public Long getUserIdOfLoggedIn() {
    String username = getUsernameOfLoggedIn();
    return getUser(username).getId();
  }

  @SneakyThrows
  public String getUsernameOfLoggedIn() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    }
    throw new UserNotLoggedInException();
  }
}
