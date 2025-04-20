package edu.sehs4701.hkdc.core.repository;

import edu.sehs4701.hkdc.core.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DentistRepository extends JpaRepository<Dentist, Long> {

    /**
     * Fetch ALL dentists along with their schedules and each schedule's clinic
     */
    @Query("""
              SELECT DISTINCT d
              FROM Dentist d
              LEFT JOIN FETCH d.schedules ds
              LEFT JOIN FETCH ds.clinic
            """)
    List<Dentist> findAllWithSchedules();

    /**
     * Fetch ONE dentist (by id) along with their schedules and each schedule's clinic
     */
    @Query("""
              SELECT d
              FROM Dentist d
              LEFT JOIN FETCH d.schedules ds
              LEFT JOIN FETCH ds.clinic
              WHERE d.id = :id
            """)
    Optional<Dentist> findByIdWithSchedules(@Param("id") Long id);
}
