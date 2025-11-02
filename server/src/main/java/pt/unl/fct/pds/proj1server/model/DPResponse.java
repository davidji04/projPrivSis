package pt.unl.fct.pds.proj1server.model;

public class DPResponse {
  private String queried_attributes;
  private double noised_response;

  public DPResponse(String attributesQueried, double noisedResponse, double querySensitivity,
      double updatedPrivacyBudget) {
    this.queried_attributes = attributesQueried;
    this.noised_response = noisedResponse;
  }

  public DPResponse() {
  }

  public String getQueried_attributes() {
    return queried_attributes;
  }

  public void setQueried_attributes(String attributesQueried) {
    this.queried_attributes = attributesQueried;
  }

  public double getNoised_response() {
    return noised_response;
  }

  public void setNoised_response(double noisedResponse) {
    this.noised_response = noisedResponse;
  }

}
