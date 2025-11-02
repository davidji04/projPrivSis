package pt.unl.fct.pds.proj1server.model;

public class CountResponse {
    private String attribute;
    private long value;

    public CountResponse() {
    }

    public CountResponse(
            String attribute,
            long value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public long getValue() {
        return value;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
