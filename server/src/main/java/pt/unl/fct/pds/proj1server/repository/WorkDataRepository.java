package pt.unl.fct.pds.proj1server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pt.unl.fct.pds.proj1server.model.WorkData;

@Repository
public interface WorkDataRepository extends JpaRepository<WorkData, Long> {
    @Query("SELECT w.postalCode, w.gender FROM WorkData w " +
           "GROUP BY w.postalCode, w.gender HAVING COUNT(w) = 1")
    List<Object[]> findUniqueQiGroups();
}
