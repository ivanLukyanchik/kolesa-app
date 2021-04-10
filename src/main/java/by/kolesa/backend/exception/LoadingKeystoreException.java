package by.kolesa.backend.exception;

public class LoadingKeystoreException extends RuntimeException {

  public LoadingKeystoreException() {
    this("Exception occurred while loading keystore");
  }

  public LoadingKeystoreException(String message) {
    super(message);
  }
}
