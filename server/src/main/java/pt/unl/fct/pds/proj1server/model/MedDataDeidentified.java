package pt.unl.fct.pds.proj1server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "med_data_di")
public final class MedDataDeidentified {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private int age;
  @Column(nullable = false)
  private String gender;
  @Column(nullable = false)
  private String postalCode;
  @Column(nullable = false)
  private String diagnosis;

  public MedDataDeidentified() {
  }

  public MedDataDeidentified(Long id,
                    int age,
                    String gender,
                    String postalCode,
                    String diagnosis) {
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.postalCode = postalCode;
        this.diagnosis = diagnosis;
    }

  public Long getId() {
    return id;
  }

  public Integer getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  public String getDiagnosis() {
    return diagnosis;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public void setDiagnosis(String diagnosis) {
    this.diagnosis = diagnosis;
  }
}