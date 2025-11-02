package pt.unl.fct.pds.proj1server.model;

import java.util.Map;

public class DPHistogramResponse {

  private String queried_attributes;

  private Map<String, Double> noised_response;

  public String getQueried_attributes() {
    return queried_attributes;
  }

  public void setQueried_attributes(String attributesQueried) {
    this.queried_attributes = attributesQueried;
  }

  public Map<String, Double> getNoised_response() {
    return noised_response;
  }

  public void setNoised_response(Map<String, Double> noisedResponse) {
    this.noised_response = noisedResponse;
  }

}