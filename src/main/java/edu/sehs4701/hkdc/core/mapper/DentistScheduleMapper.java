package edu.sehs4701.hkdc.core.mapper;

import edu.sehs4701.hkdc.core.model.DentistSchedule;
import edu.sehs4701.hkdc.core.payload.response.DentistResponseDto;
import edu.sehs4701.hkdc.core.payload.response.DentistScheduleResponseDto;
import org.springframework.stereotype.Component;

@Component
public class DentistScheduleMapper {

    public static DentistScheduleResponseDto toDto(DentistSchedule schedule) {
        DentistScheduleResponseDto dto = new DentistScheduleResponseDto();
        dto.setDayOfWeek(schedule.getDayOfWeek().name());
        dto.setStartTime(schedule.getStartTime().toString());
        dto.setEndTime(schedule.getEndTime().toString());

        DentistResponseDto dentistDto = new DentistResponseDto();
        dentistDto.setFirstName(schedule.getDentist().getFirstName());
        dentistDto.setLastName(schedule.getDentist().getLastName());
        dentistDto.setSchedules(null);
        dto.setDentist(dentistDto);

        return dto;
    }

}
