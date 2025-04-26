package edu.sehs4701.hkdc.controller;

import edu.sehs4701.hkdc.core.model.User;
import edu.sehs4701.hkdc.core.payload.request.BookingRequestDto;
import edu.sehs4701.hkdc.core.payload.response.BookingResponseDto;
import edu.sehs4701.hkdc.core.repository.UserRepository;
import edu.sehs4701.hkdc.core.service.BookingService;
import edu.sehs4701.hkdc.core.service.impl.EmailSenderService;
import edu.sehs4701.hkdc.core.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;

    @GetMapping("/new")
    public String bookingForm(Model model, Authentication auth) {
        User user = (User) auth.getPrincipal();
        if (user.getRole() != Role.PATIENT) {
            return "redirect:/login";
        }

        model.addAttribute("appointmentSlots", bookingService.getAvailableAppointmentSlots());
        model.addAttribute("services", bookingService.getAllServices());
        model.addAttribute("currentUser", user);

        return "booking_form";
    }

    @PostMapping
    public String submitBooking(
            @ModelAttribute BookingRequestDto req,
            Authentication auth
    ) {
        bookingService.createBooking(req, (User) auth.getPrincipal());
        return "redirect:/bookings/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmation(Authentication auth, Model model) {
        User currentUser = (User) auth.getPrincipal();
        String userEmail = currentUser.getEmail();

        // Retrieve bookings for the current user
        List<BookingResponseDto> bookings = bookingService.getBookingsForUser(currentUser);

        if (!bookings.isEmpty()) {
            BookingResponseDto latestBooking = bookings.get(0);

            // Prepare email details
            String subject = "Booking Confirmation - " + latestBooking.getId();
            String body = String.format("Dear %s %s,\n\nThank you for your booking!\n\n"
                            + "Here are your booking details:\n"
                            + "Clinic Name: %s\n"
                            + "Dental Service: %s\n"
                            + "Dentist: Dr. %s %s\n"
                            + "Date: %s (%s)\n"
                            + "Time: %s - %s\n\n"
                            + "We look forward to seeing you!\n\n"
                            + "Best regards,\nYour Booking Team",
                    latestBooking.getUserFirstName(),
                    latestBooking.getUserLastName(),
                    latestBooking.getClinicName(),
                    latestBooking.getServiceName(),
                    latestBooking.getDentistFirstName(),
                    latestBooking.getDentistLastName(),
                    latestBooking.getDate(),
                    latestBooking.getDayOfWeek(),
                    latestBooking.getStartTime(),
                    latestBooking.getEndTime());


            emailSenderService.sendSimpleEmail(userEmail, subject, body);
        }

        model.addAttribute("currentUser", currentUser);
        return "booking_confirmation";
    }

    @GetMapping
    public String listBookings(Model model, Authentication auth) {
        User u = (User) auth.getPrincipal();
        if (u.getRole() != Role.PATIENT) {
            return "redirect:/login";
        }
        List<BookingResponseDto> dtos = bookingService.getBookingsForUser(u);
        model.addAttribute("bookings", dtos);
        model.addAttribute("currentUser", u);
        return "booking_list";
    }
}