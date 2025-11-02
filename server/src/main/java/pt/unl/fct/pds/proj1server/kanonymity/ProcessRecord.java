package pt.unl.fct.pds.proj1server.kanonymity;

import pt.unl.fct.pds.proj1server.model.MedDataDeidentified;

public class ProcessRecord {
    String diagnosis; 
    int age;
    String gender;
    String postalCode; 
    MedDataDeidentified originalRecord; 

    ProcessRecord(MedDataDeidentified original) {
        this.originalRecord = original;
        this.diagnosis = original.getDiagnosis();
        this.gender = original.getGender();
        
        if (original.getAge() > 90) {
            this.age = 90;
        } else if (original.getAge() < 18) {
            this.age = 18;
        } else {
            this.age = original.getAge();
        }

        if (original.getPostalCode() != null && original.getPostalCode().length() >= 3) {
            this.postalCode = original.getPostalCode().substring(0, 2) + "xxxxxxx";
        } else {
            this.postalCode = "*";
        }
    }
    public Comparable getValue(String dimension) {
        return switch (dimension) {
            case "age" -> this.age;
            case "gender" -> this.gender;
            case "postalCode" -> this.postalCode;
            default -> throw new IllegalArgumentException("Dimensão desconhecida");
        };
    }
}