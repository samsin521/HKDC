package edu.sehs4701.hkdc.core.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DentistScheduleResponseDto {
    private DentistResponseDto dentist;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}
