package pt.unl.fct.pds.proj1server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pt.unl.fct.pds.proj1server.model.MedDataKAnon;

@Repository
public interface MedDataKAnonRepository extends JpaRepository<MedDataKAnon, Long> {
    
  @Query("SELECT m.postalCode, m.gender FROM MedDataKAnon m " +
              "GROUP BY m.postalCode, m.gender HAVING COUNT(m) = 1")
  List<Object[]> findUniqueQiGroups();
}
