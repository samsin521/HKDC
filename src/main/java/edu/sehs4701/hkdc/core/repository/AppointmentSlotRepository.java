package edu.sehs4701.hkdc.core.repository;

import edu.sehs4701.hkdc.core.model.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {

    List<AppointmentSlot> findByDentistScheduleClinicIdAndIsBookedFalse(Long clinicId);

    List<AppointmentSlot> findByIsBookedFalseAndDateAfter(LocalDate now);
}
