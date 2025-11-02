package pt.unl.fct.pds.proj1server.model;

public class AverageRequest {
  private String attribute;
  private String value;

  public AverageRequest() {
  }

  public AverageRequest(
      String attribute,
      String value) {
    this.attribute = attribute;
    this.value = value;
  }

  public String getAttribute() {
    return attribute;
  }

  public String getValue() {
    return value;
  }

  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
