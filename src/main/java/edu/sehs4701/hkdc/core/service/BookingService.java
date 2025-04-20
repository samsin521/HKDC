package edu.sehs4701.hkdc.core.service;

import edu.sehs4701.hkdc.core.model.AppointmentSlot;
import edu.sehs4701.hkdc.core.model.DentalService;
import edu.sehs4701.hkdc.core.model.User;
import edu.sehs4701.hkdc.core.payload.request.BookingRequestDto;
import edu.sehs4701.hkdc.core.payload.response.AppointmentSlotResponseDto;
import edu.sehs4701.hkdc.core.payload.response.BookingResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ClinicScheduleResponseDto;

import java.util.List;

public interface BookingService {

    List<ClinicScheduleResponseDto> getClinicSchedulesWithSlots();

    List<DentalService> getAllServices();

    void createBooking(BookingRequestDto req, User currentUser);

    List<BookingResponseDto> getBookingsForUser(User currentUser);

    List<AppointmentSlotResponseDto> getAvailableAppointmentSlots();
}
