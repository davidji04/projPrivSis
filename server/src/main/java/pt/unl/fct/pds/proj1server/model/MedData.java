package pt.unl.fct.pds.proj1server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "med_data")
public class MedData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String diagnosis;

    public MedData() {
    }

    public MedData(Long id,
            String name,
            int age,
            String address,
            String email,
            String gender,
            String postalCode,
            String diagnosis) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.postalCode = postalCode;
        this.diagnosis = diagnosis;
    }
    
    public Long getId() {return id;}
    public String getName() {return name;}
    public int getAge() {return age;}
    public String getAddress() {return address;}
    public String getEmail() {return email;}
    public String getGender() {return gender;}
    public String getPostalCode() {return postalCode;}
    public String getDiagnosis() {return diagnosis;}
    
    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setAddress(String Address) {this.address = address;}
    public void setAge(int age) {this.age = age;}
    public void setEmail(String email) {this.email = email;}
    public void setGender(String gender) {this.gender = gender;}
    public void setPostalCode(String postalCode) {this.postalCode = postalCode;}
    public void setDiagnosis(String Diagnosis) {this.diagnosis = diagnosis;}
}
