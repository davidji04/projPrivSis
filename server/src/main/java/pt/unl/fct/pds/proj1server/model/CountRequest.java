package pt.unl.fct.pds.proj1server.model;

import jakarta.persistence.*;

public class CountRequest {
    private String attribute;
    private String value;

    public CountRequest() {
    }

    public CountRequest(
        String attribute,
        String value) {
        this.attribute = attribute;
        this.value = value;
    }
    
    public String getAttribute() {return attribute;}
    public String getValue() {return value;}
    
    public void setAttribute(String attribute) {this.attribute = attribute;}
    public void setValue(String value) {this.value = value;}
}
