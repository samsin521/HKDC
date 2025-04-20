package edu.sehs4701.hkdc.core.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClinicDetailResponseDto {
    private ClinicResponseDto clinic;
    private List<DentistScheduleResponseDto> schedules;
    private List<AppointmentSlotResponseDto> slots;
}
