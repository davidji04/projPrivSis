package pt.unl.fct.pds.proj1server.deidentification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

  @Repository
  public interface MedDataDeidentifiedRepository extends JpaRepository<MedDataDeidentified, Long> {
    // Custom query methods can be defined here
    // isto tambem existe nas outras classes
  }
}