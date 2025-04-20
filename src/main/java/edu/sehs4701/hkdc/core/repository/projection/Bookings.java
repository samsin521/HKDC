package edu.sehs4701.hkdc.core.repository.projection;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public interface Bookings {
    Long getId();

    LocalDate getDate();

    DayOfWeek getDayOfWeek();

    String getPatientFirstName();

    String getPatientLastName();

    String getDentalServiceName();

    String getClinicName();

    String getDentistFirstName();

    String getDentistLastName();

    LocalTime getStartTime();

    LocalTime getEndTime();
}
