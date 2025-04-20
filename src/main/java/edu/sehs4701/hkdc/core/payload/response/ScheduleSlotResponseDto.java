package edu.sehs4701.hkdc.core.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
public class ScheduleSlotResponseDto {
    private Long id;
    private String dentistFirstName;
    private String dentistLastName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

}
