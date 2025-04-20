package edu.sehs4701.hkdc.controller;

import edu.sehs4701.hkdc.core.model.User;
import edu.sehs4701.hkdc.core.payload.request.BookingRequestDto;
import edu.sehs4701.hkdc.core.payload.response.BookingResponseDto;
import edu.sehs4701.hkdc.core.service.BookingService;
import edu.sehs4701.hkdc.core.type.Role;
import lombok.NonNull;
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

    @NonNull
    private final BookingService bookingService;

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
    public String confirmation(Authentication auth, Model m) {
        m.addAttribute("currentUser", auth.getPrincipal());
        return "booking_confirmation";
    }

    @GetMapping
    public String listBookings(Model model, Authentication auth) {
        User u = (User) auth.getPrincipal();
        if (u.getRole() != Role.PATIENT) {
            return "redirect:/login";
        }
        List<BookingResponseDto> dtos =
                bookingService.getBookingsForUser(u);
        model.addAttribute("bookings", dtos);
        model.addAttribute("currentUser", u);
        return "booking_list";
    }
}
