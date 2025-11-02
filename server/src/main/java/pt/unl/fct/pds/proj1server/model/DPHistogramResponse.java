package pt.unl.fct.pds.proj1server.model;

import java.util.Map;

public class DPHistogramResponse {

  private String attributesQueried;

  private Map<String, Double> noisedResponse;

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

}