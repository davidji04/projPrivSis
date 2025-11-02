package pt.unl.fct.pds.proj1server.model;

public class DPResponse {
  private String attributesQueried;
  private double noisedResponse;
  private double querySensitivity;
  private double updatedPrivacyBudget;

  public DPResponse(String attributesQueried, double noisedResponse, double querySensitivity,
      double updatedPrivacyBudget) {
    this.attributesQueried = attributesQueried;
    this.noisedResponse = noisedResponse;
    this.querySensitivity = querySensitivity;
    this.updatedPrivacyBudget = updatedPrivacyBudget;
  }

  public DPResponse() {
  }

  public String getAttributesQueried() {
    return attributesQueried;
  }

  public void setAttributesQueried(String attributesQueried) {
    this.attributesQueried = attributesQueried;
  }

  public double getNoisedResponse() {
    return noisedResponse;
  }

  public void setNoisedResponse(double noisedResponse) {
    this.noisedResponse = noisedResponse;
  }

  public double getQuerySensitivity() {
    return querySensitivity;
  }

  public void setQuerySensitivity(double querySensitivity) {
    this.querySensitivity = querySensitivity;
  }

  public double getUpdatedPrivacyBudget() {
    return updatedPrivacyBudget;
  }

  public void setUpdatedPrivacyBudget(double updatedPrivacyBudget) {
    this.updatedPrivacyBudget = updatedPrivacyBudget;
  }

}
