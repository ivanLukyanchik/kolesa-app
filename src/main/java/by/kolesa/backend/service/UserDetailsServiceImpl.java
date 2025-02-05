package by.kolesa.backend.service;

import by.kolesa.backend.entity.User;
import by.kolesa.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    User user =
        userOptional.orElseThrow(
            () -> new UsernameNotFoundException("No user found " + "with username : " + username));
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.isEnabled(),
        true,
        true,
        true,
        getAuthorities("USER"));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(String role) {
    return Collections.singletonList(new SimpleGrantedAuthority(role));
  }
}
