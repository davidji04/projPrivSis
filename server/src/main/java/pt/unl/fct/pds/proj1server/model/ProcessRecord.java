package pt.unl.fct.pds.proj1server.model;

public class ProcessRecord {
    private String diagnosis; 
    private int age;
    private String gender;
    private String postalCode; 
    private MedDataDeidentified originalRecord; 

    public ProcessRecord(MedDataDeidentified original) {
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
    public String getDiagnosis() {
        return diagnosis;
    }
    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPostalCode() {
        return postalCode;
    }
}