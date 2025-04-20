package edu.sehs4701.hkdc.core.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ClinicScheduleResponseDto {
    private Long clinicId;
    private String clinicName;
    private String clinicAddress;
    private List<ScheduleSlotResponseDto> slots;
}
