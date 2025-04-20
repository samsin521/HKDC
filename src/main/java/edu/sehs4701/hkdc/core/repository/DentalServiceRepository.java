package edu.sehs4701.hkdc.core.repository;

import edu.sehs4701.hkdc.core.model.DentalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentalServiceRepository extends JpaRepository<DentalService, Long> {
}
