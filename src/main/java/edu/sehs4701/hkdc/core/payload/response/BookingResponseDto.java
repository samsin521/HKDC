package edu.sehs4701.hkdc.core.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookingResponseDto {
    private Long id;
    private LocalDate date;
    private DayOfWeek dayOfWeek;
    private String serviceName;
    private String dentistFirstName;
    private String dentistLastName;
    private String clinicName;
    private String clinicAddress;
    private LocalTime startTime;
    private LocalTime endTime;
}
