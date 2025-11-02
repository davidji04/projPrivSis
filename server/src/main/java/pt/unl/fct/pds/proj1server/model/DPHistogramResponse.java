package pt.unl.fct.pds.proj1server.model;

import java.util.Map;

public class DPHistogramResponse {

  private String attributesQueried;

  private Map<String, Double> noisedResponse;

  private double querySensitivity;

  private double updatedPrivacyBudget;

  public String getAttributesQueried() {
    return attributesQueried;
  }

  public void setAttributesQueried(String attributesQueried) {
    this.attributesQueried = attributesQueried;
  }

  public Map<String, Double> getNoisedResponse() {
    return noisedResponse;
  }

  public void setNoisedResponse(Map<String, Double> noisedResponse) {
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