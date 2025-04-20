package edu.sehs4701.hkdc.controller;

import edu.sehs4701.hkdc.core.payload.response.DentistResponseDto;
import edu.sehs4701.hkdc.core.service.DentistService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dentists")
public class DentistController {

    @NonNull
    private final DentistService dentistService;

    @GetMapping
    public String listDentists(Model model, Authentication auth) {
        if (auth != null) model.addAttribute("currentUser", auth.getPrincipal());
        List<DentistResponseDto> dtos = dentistService.listAll();
        model.addAttribute("dentists", dtos);
        return "dentists_list";
    }

    @GetMapping("/{id}/schedule")
    public String dentistSchedule(
            @PathVariable Long id,
            Model model,
            Authentication auth
    ) {
        if (auth != null) model.addAttribute("currentUser", auth.getPrincipal());
        DentistResponseDto dto;
        try {
            dto = dentistService.getById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        model.addAttribute("dentist", dto);
        model.addAttribute("schedules", dto.getSchedules());
        return "dentists_schedule";
    }
}
