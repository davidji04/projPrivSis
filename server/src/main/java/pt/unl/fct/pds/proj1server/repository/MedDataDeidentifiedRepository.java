package pt.unl.fct.pds.proj1server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.unl.fct.pds.proj1server.model.MedDataDeidentified;

  @Repository
  public interface MedDataDeidentifiedRepository extends JpaRepository<MedDataDeidentified, Long> {
    
    
  }
