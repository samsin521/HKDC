package edu.sehs4701.hkdc.core.mapper;

import edu.sehs4701.hkdc.core.model.Dentist;
import edu.sehs4701.hkdc.core.model.DentistSchedule;
import edu.sehs4701.hkdc.core.payload.response.DentistResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ScheduleResponseDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DentistMapper {

    public static DentistResponseDto toResponse(Dentist d) {
        DentistResponseDto dto = new DentistResponseDto();
        dto.setId(d.getId());
        dto.setEmail(d.getEmail());
        dto.setFirstName(d.getFirstName());
        dto.setLastName(d.getLastName());
        dto.setSchedules(
                d.getSchedules().stream()
                        .map(DentistMapper::toScheduleResponse)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public static ScheduleResponseDto toScheduleResponse(DentistSchedule s) {
        ScheduleResponseDto dto = new ScheduleResponseDto();
        dto.setId(s.getId());
        dto.setDayOfWeek(s.getDayOfWeek());
        dto.setStartTime(s.getStartTime());
        dto.setEndTime(s.getEndTime());
        dto.setClinic(ClinicMapper.toDto(s.getClinic()));
        return dto;
    }
}
