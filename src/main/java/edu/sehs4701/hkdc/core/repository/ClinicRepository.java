package edu.sehs4701.hkdc.core.repository;

import edu.sehs4701.hkdc.core.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    Optional<Clinic> findByName(String clinic);

    @Query("""
                SELECT c 
                FROM Clinic c 
                LEFT JOIN FETCH c.schedules ds
            """)
    List<Clinic> findAllWithSchedules();

}
