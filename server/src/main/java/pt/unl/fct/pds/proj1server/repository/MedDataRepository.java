package pt.unl.fct.pds.proj1server.repository;

import pt.unl.fct.pds.proj1server.model.MedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedDataRepository extends JpaRepository<MedData, Long> {
        @Query("SELECT SUM(CASE " +
                        "WHEN m.age > :max THEN :max " +
                        "WHEN m.age < :min THEN :min " +
                        "ELSE m.age END) " +
                        "FROM MedData m")
        Double getSumOfAgeWithClipping(@Param("min") double min, @Param("max") double max);

        Long countByDiagnosis(String diagnosis); // Método para contar entradas por diagnóstico é automatico

        @Query("SELECT SUM(CASE " +
                        "WHEN m.age > :max THEN :max " +
                        "WHEN m.age < :min THEN :min " +
                        "ELSE m.age END) " +
                        "FROM MedData m WHERE m.diagnosis = :diagnosis")
        Double getSumOfAgeWithClippingAndDiagnosis(
                        @Param("min") double min,
                        @Param("max") double max,
                        @Param("diagnosis") String diagnosis);

        Long countByGender(String gender);

        @Query("SELECT SUM(CASE " +
                        "WHEN m.age > :max THEN :max " +
                        "WHEN m.age < :min THEN :min " +
                        "ELSE m.age END) " +
                        "FROM MedData m WHERE m.gender = :gender")
        Double getSumOfAgeWithClippingAndGender(
                        @Param("min") double min,
                        @Param("max") double max,
                        @Param("gender") String gender);

        // --- Para o Histograma de Faixas Etárias (Baixa Cardinalidade) ---
        @Query("SELECT COUNT(m) FROM MedData m WHERE m.age >= :min AND m.age <= :max")
        Long countByAgeRange(@Param("min") int min, @Param("max") int max);
}
