package my.project.salestaxes.exceptions;

public class TokenNotFoundException extends RuntimeException {

  public TokenNotFoundException(final String message) {
    super(message);
  }
}
