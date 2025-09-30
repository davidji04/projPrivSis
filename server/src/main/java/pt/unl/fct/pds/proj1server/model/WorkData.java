package pt.unl.fct.pds.proj1server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "work_data")
public class WorkData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String fName;
    @Column(nullable = false)
    private String lName;
    @Column(nullable = false)
    private String postalCode;
    @Column(nullable = false)
    private String education;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private String workplace;
    @Column(nullable = false)
    private String department;

    public WorkData() {
    }

    public WorkData(Long id,
                    String fName,
                    String lName,
                    String postalCode,
                    String education,
                    String gender,
                    String workplace,
                    String department) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.postalCode = postalCode;
        this.education = education;
        this.gender = gender;
        this.workplace = workplace;
        this.department = department;
    }
    
    public Long getId() {return id;}
    public String getFName() {return fName;}
    public String getLName() {return lName;}
    public String getPostalCode() {return postalCode;}
    public String getEducation() {return education;}
    public String getGender() {return gender;}
    public String getWorkplace() {return workplace;}
    public String getDepartment() {return department;}
    
    public void setId(Long id) {this.id = id;}
    public void setFName(String fName) {this.fName = fName;}
    public void setLName(String lName) {this.lName = lName;}
    public void setPostalCode(String postalCode) {this.postalCode = postalCode;}
    public void setEducation(String education) {this.education = education;}
    public void setGender(String gender) {this.gender = gender;}
    public void setWorkplace(String workplace) {this.workplace = workplace;}
    public void setDepartment(String department) {this.department = department;}
}
