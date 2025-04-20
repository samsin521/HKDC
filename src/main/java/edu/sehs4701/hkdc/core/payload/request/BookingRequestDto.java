package edu.sehs4701.hkdc.core.payload.request;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookingRequestDto {
    private Long clinicId;
    private Long scheduleId;
    private Long serviceId;
    private Long slotId;
}
