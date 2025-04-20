package edu.sehs4701.hkdc.core.repository.projection;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface ScheduleOption {
    Long getId();

    String getDentistFirstName();

    String getDentistLastName();

    DayOfWeek getDayOfWeek();

    LocalTime getStartTime();

    LocalTime getEndTime();

    Long getClinicId();

    String getClinicName();
}
