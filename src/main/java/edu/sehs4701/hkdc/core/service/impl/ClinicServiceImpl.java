package edu.sehs4701.hkdc.core.service.impl;

import edu.sehs4701.hkdc.core.mapper.AppointmentSlotMapper;
import edu.sehs4701.hkdc.core.mapper.ClinicMapper;
import edu.sehs4701.hkdc.core.mapper.DentistScheduleMapper;
import edu.sehs4701.hkdc.core.model.Clinic;
import edu.sehs4701.hkdc.core.model.DentistSchedule;
import edu.sehs4701.hkdc.core.payload.response.AppointmentSlotResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ClinicDetailResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ClinicResponseDto;
import edu.sehs4701.hkdc.core.payload.response.DentistScheduleResponseDto;
import edu.sehs4701.hkdc.core.repository.ClinicRepository;
import edu.sehs4701.hkdc.core.repository.DentistScheduleRepository;
import edu.sehs4701.hkdc.core.service.ClinicService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    @NonNull
    private final ClinicRepository clinicRepository;

    @NonNull
    private final DentistScheduleRepository dentistScheduleRepository;

    @NonNull
    private final AppointmentSlotMapper appointmentSlotMapper;

    @Override
    public Optional<ClinicDetailResponseDto> getClinicDetail(Long clinicId) {
        Optional<Clinic> clinicOptional = clinicRepository.findById(clinicId);
        if (clinicOptional.isEmpty()) return Optional.empty();

        Clinic clinic = clinicOptional.get();

        List<DentistSchedule> dentistSchedules = dentistScheduleRepository.findByClinicIdWithDentistAndClinic(clinicId);
        List<DentistScheduleResponseDto> scheduleDtos = dentistSchedules.stream()
                .map(DentistScheduleMapper::toDto)
                .collect(Collectors.toList());

        List<AppointmentSlotResponseDto> slotDtos = dentistSchedules.stream()
                .flatMap(schedule -> schedule.getSlots().stream())
                .filter(slot -> !slot.getIsBooked())
                .map(appointmentSlotMapper::toDto)
                .toList();

        ClinicDetailResponseDto dto = new ClinicDetailResponseDto();
        dto.setClinic(ClinicMapper.toDto(clinic));
        dto.setSchedules(scheduleDtos);
        dto.setSlots(slotDtos);

        return Optional.of(dto);
    }

    @Override
    public List<ClinicResponseDto> getAllClinics() {
        List<Clinic> clinicList = clinicRepository.findAllWithSchedules();

        return clinicList.stream()
                .map(ClinicMapper::toDto)
                .collect(Collectors.toList());
    }
}
