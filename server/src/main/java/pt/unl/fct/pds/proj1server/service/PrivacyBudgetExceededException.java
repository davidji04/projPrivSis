package pt.unl.fct.pds.proj1server.service;

public class PrivacyBudgetExceededException extends RuntimeException {

  public PrivacyBudgetExceededException(String message) {
    super(message);
  }
}
