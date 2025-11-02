package pt.unl.fct.pds.proj1server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pt.unl.fct.pds.proj1server.model.MedData;

@Repository
public interface MedDataRepository extends JpaRepository<MedData, Long> {
    // Custom query methods can be defined here
}
