package pt.unl.fct.pds.proj1server.repository;

import pt.unl.fct.pds.proj1server.model.WorkData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkDataRepository extends JpaRepository<WorkData, Long> {
    // Custom query methods can be defined here
}
