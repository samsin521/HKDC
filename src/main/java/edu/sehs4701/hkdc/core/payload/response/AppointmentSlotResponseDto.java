package edu.sehs4701.hkdc.core.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentSlotResponseDto {
    private Long id;
    private String dentistName;
    private String clinicName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
