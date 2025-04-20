package edu.sehs4701.hkdc.core.mapper;

import edu.sehs4701.hkdc.core.model.AppointmentSlot;
import edu.sehs4701.hkdc.core.payload.response.AppointmentSlotResponseDto;
import org.springframework.stereotype.Component;

@Component
public class AppointmentSlotMapper {

    public AppointmentSlotResponseDto toDto(AppointmentSlot slot) {
        AppointmentSlotResponseDto dto = new AppointmentSlotResponseDto();
        dto.setId(slot.getId());
        dto.setDentistName(slot.getDentistSchedule().getDentist().getFirstName() + " " +
                slot.getDentistSchedule().getDentist().getLastName());
        dto.setClinicName(slot.getDentistSchedule().getClinic().getName());
        dto.setDate(slot.getDate());
        dto.setStartTime(slot.getStartTime());
        dto.setEndTime(slot.getEndTime());
        return dto;
    }
}
