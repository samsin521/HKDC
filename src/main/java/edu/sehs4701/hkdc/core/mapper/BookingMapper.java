package edu.sehs4701.hkdc.core.mapper;

import edu.sehs4701.hkdc.core.model.Clinic;
import edu.sehs4701.hkdc.core.payload.response.BookingResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ClinicScheduleResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ScheduleSlotResponseDto;
import edu.sehs4701.hkdc.core.repository.projection.Bookings;
import edu.sehs4701.hkdc.core.repository.projection.ScheduleOption;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    public BookingResponseDto toBookingDto(Bookings p) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(p.getId());
        dto.setDate(p.getDate());
        dto.setDayOfWeek(p.getDayOfWeek());
        dto.setServiceName(p.getDentalServiceName());
        dto.setDentistFirstName(p.getDentistFirstName());
        dto.setDentistLastName(p.getDentistLastName());
        dto.setClinicName(p.getClinicName());
        dto.setClinicAddress(p.getClinicName());
        dto.setStartTime(p.getStartTime());
        dto.setEndTime(p.getEndTime());
        return dto;
    }
}
