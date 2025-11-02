package pt.unl.fct.pds.proj1server.model;

public class DPResponse {
  private String attributesQueried;
  private double noisedResponse;

  public DPResponse(String attributesQueried, double noisedResponse) {
    this.attributesQueried = attributesQueried;
    this.noisedResponse = noisedResponse;
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

}
