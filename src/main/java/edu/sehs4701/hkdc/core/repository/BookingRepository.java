package edu.sehs4701.hkdc.core.repository;

import edu.sehs4701.hkdc.core.model.Booking;
import edu.sehs4701.hkdc.core.model.DentistSchedule;
import edu.sehs4701.hkdc.core.model.User;
import edu.sehs4701.hkdc.core.repository.projection.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT
              b.id                                AS id,
              b.date                              AS date,
              b.dentistSchedule.dayOfWeek         AS dayOfWeek,
              b.patient.user.firstName            AS patientFirstName,
              b.patient.user.lastName             AS patientLastName,
              b.dentalService.name                AS dentalServiceName,
              b.clinic.name                       AS clinicName,
              b.dentistSchedule.dentist.firstName AS dentistFirstName,
              b.dentistSchedule.dentist.lastName  AS dentistLastName,
              b.dentistSchedule.startTime         AS startTime,
              b.dentistSchedule.endTime           AS endTime
            FROM Booking b
            WHERE b.patient.user = :user
            """)
    List<Bookings> findAllWithDetailsByCurrentUser(@Param("user") User user);

    boolean existsByDentistScheduleAndDateAndStartTime(DentistSchedule schedule, LocalDate date, LocalTime startTime);

    List<Booking> findByDentistScheduleIdAndDate(Long id, LocalDate date);

    @Query("SELECT b FROM Booking b WHERE b.patient.id = :patientId")
    List<Bookings> findBookingsByPatientId(Long patientId);

}
