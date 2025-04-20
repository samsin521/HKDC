package edu.sehs4701.hkdc.core.mapper;

import edu.sehs4701.hkdc.core.model.Clinic;
import edu.sehs4701.hkdc.core.payload.response.ClinicResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ClinicMapper {

    public static ClinicResponseDto toDto(Clinic clinic) {
        ClinicResponseDto dto = new ClinicResponseDto();
        dto.setId(clinic.getId());
        dto.setName(clinic.getName());
        dto.setAddress(clinic.getAddress());
        dto.setOpenHours(clinic.getOpenHours());
        return dto;
    }

}
