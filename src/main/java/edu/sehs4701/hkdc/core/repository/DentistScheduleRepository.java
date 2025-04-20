package edu.sehs4701.hkdc.core.repository;

import edu.sehs4701.hkdc.core.model.DentistSchedule;
import edu.sehs4701.hkdc.core.repository.projection.ScheduleOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DentistScheduleRepository extends JpaRepository<DentistSchedule, Long> {

    @Query("SELECT ds FROM DentistSchedule ds " +
            "JOIN FETCH ds.dentist " +
            "JOIN FETCH ds.clinic " +
            "LEFT JOIN FETCH ds.slots " +
            "WHERE ds.clinic.id = :clinicId")
    List<DentistSchedule> findByClinicIdWithDentistAndClinic(@Param("clinicId") Long clinicId);

}
