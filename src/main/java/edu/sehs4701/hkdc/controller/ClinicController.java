package edu.sehs4701.hkdc.controller;

import edu.sehs4701.hkdc.core.payload.response.ClinicDetailResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ClinicResponseDto;
import edu.sehs4701.hkdc.core.service.ClinicService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clinics")
public class ClinicController {

    @NonNull
    private final ClinicService clinicService;

    @GetMapping
    public String listClinics(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("currentUser", authentication.getPrincipal());
        }

        List<ClinicResponseDto> allClinics = clinicService.getAllClinics();
        model.addAttribute("clinics", allClinics);

        return "clinics_list";
    }

    @GetMapping("/{clinicId}")
    public String clinicDetail(@PathVariable Long clinicId, Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("currentUser", authentication.getPrincipal());
        }

        Optional<ClinicDetailResponseDto> optionalClinicDetail = clinicService.getClinicDetail(clinicId);

        if (optionalClinicDetail.isPresent()) {
            ClinicDetailResponseDto clinicDetailDto = optionalClinicDetail.get();
            model.addAttribute("clinic", clinicDetailDto.getClinic());
            model.addAttribute("schedules", clinicDetailDto.getSchedules());
            model.addAttribute("slots", clinicDetailDto.getSlots());
        } else {
            model.addAttribute("errorMessage", "Clinic not found.");
        }

        return "clinics_detail";
    }
}
