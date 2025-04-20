package edu.sehs4701.hkdc.core.repository;

import edu.sehs4701.hkdc.core.model.Patient;
import edu.sehs4701.hkdc.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUser(User user);
}
