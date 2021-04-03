package by.kolesa.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KolesaApplication {

  public static void main(String[] args) {
    SpringApplication.run(KolesaApplication.class, args);
  }
}
