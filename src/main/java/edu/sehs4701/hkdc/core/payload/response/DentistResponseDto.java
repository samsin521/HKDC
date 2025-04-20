package edu.sehs4701.hkdc.core.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DentistResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<ScheduleResponseDto> schedules;
}
