package edu.sehs4701.hkdc.core.service.impl;

import edu.sehs4701.hkdc.core.mapper.AppointmentSlotMapper;
import edu.sehs4701.hkdc.core.mapper.BookingMapper;
import edu.sehs4701.hkdc.core.model.AppointmentSlot;
import edu.sehs4701.hkdc.core.model.Booking;
import edu.sehs4701.hkdc.core.model.Clinic;
import edu.sehs4701.hkdc.core.model.DentalService;
import edu.sehs4701.hkdc.core.model.Patient;
import edu.sehs4701.hkdc.core.model.User;
import edu.sehs4701.hkdc.core.payload.request.BookingRequestDto;
import edu.sehs4701.hkdc.core.payload.response.AppointmentSlotResponseDto;
import edu.sehs4701.hkdc.core.payload.response.BookingResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ClinicScheduleResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ScheduleSlotResponseDto;
import edu.sehs4701.hkdc.core.repository.AppointmentSlotRepository;
import edu.sehs4701.hkdc.core.repository.BookingRepository;
import edu.sehs4701.hkdc.core.repository.ClinicRepository;
import edu.sehs4701.hkdc.core.repository.DentalServiceRepository;
import edu.sehs4701.hkdc.core.repository.PatientRepository;
import edu.sehs4701.hkdc.core.repository.projection.Bookings;
import edu.sehs4701.hkdc.core.service.BookingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {



    @NonNull
    private final ClinicRepository clinicRepo;

    @NonNull
    private final DentalServiceRepository serviceRepo;

    @NonNull
    private final AppointmentSlotMapper appointmentSlotMapper;

    @NonNull
    private final PatientRepository patientRepo;

    @NonNull
    private final BookingRepository bookingRepo;

    @NonNull
    private final BookingMapper mapper;

    @NonNull
    private final AppointmentSlotRepository appointmentSlotRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClinicScheduleResponseDto> getClinicSchedulesWithSlots() {
        List<Clinic> clinics = clinicRepo.findAll();

        return clinics.stream()
                .map(clinic -> {
                    List<AppointmentSlot> availableSlots =
                            appointmentSlotRepository.findByDentistScheduleClinicIdAndIsBookedFalse(clinic.getId());

                    List<ScheduleSlotResponseDto> slots = availableSlots.stream()
                            .map(slot -> new ScheduleSlotResponseDto(
                                    slot.getId(),
                                    slot.getDentistSchedule().getDentist().getFirstName(),
                                    slot.getDentistSchedule().getDentist().getLastName(),
                                    slot.getDate(),
                                    slot.getStartTime(),
                                    slot.getEndTime()
                            ))
                            .toList();

                    return new ClinicScheduleResponseDto(
                            clinic.getId(),
                            clinic.getName(),
                            clinic.getAddress(),
                            slots
                    );
                })
                .filter(dto -> !dto.getSlots().isEmpty())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DentalService> getAllServices() {
        return serviceRepo.findAll();
    }

    @Override
    @Transactional
    public void createBooking(BookingRequestDto req, User currentUser) {
        Patient p = patientRepo.findByUser(currentUser)
                .orElseThrow(() -> new IllegalStateException("No patient for user"));

        AppointmentSlot slot = appointmentSlotRepository.findById(req.getSlotId())
                .orElseThrow(() -> new IllegalStateException("Slot not found"));

        if (slot.getIsBooked()) {
            throw new IllegalStateException("Time slot already booked.");
        }

        Booking booking = new Booking();
        booking.setPatient(p);
        booking.setClinic(slot.getDentistSchedule().getClinic());
        booking.setDentistSchedule(slot.getDentistSchedule());
        booking.setDentalService(serviceRepo.findById(req.getServiceId()).orElseThrow());
        booking.setDate(slot.getDate());
        booking.setStartTime(slot.getStartTime());
        booking.setEndTime(slot.getEndTime());
        booking.setSlot(slot);

        slot.setIsBooked(true);

        bookingRepo.save(booking);
        appointmentSlotRepository.save(slot);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponseDto> getBookingsForUser(User currentUser) {
        return bookingRepo.findAllWithDetailsByCurrentUser(currentUser).stream()
                .map(mapper::toBookingDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSlotResponseDto> getAvailableAppointmentSlots() {
        return appointmentSlotRepository.findByIsBookedFalseAndDateAfter(LocalDate.now()).stream()
                .map(appointmentSlotMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Bookings> getBookingsForEmail(User currentUser) {
        return bookingRepo.findBookingsByPatientId(currentUser.getId());
    }

}
