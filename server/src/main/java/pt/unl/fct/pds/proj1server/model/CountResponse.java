package pt.unl.fct.pds.proj1server.model;

import jakarta.persistence.*;

public class CountResponse {
    private String attribute;
    private int value;

    public CountResponse() {
    }

    public CountResponse(
        String attribute,
        int value) {
        this.attribute = attribute;
        this.value = value;
    }
    
    public String getAttribute() {return attribute;}
    public int getValue() {return value;}
    
    public void setAttribute(String attribute) {this.attribute = attribute;}
    public void setValue(int value) {this.value = value;}
}
