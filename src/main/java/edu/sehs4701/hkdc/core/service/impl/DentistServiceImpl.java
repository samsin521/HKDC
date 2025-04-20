package edu.sehs4701.hkdc.core.service.impl;

import edu.sehs4701.hkdc.core.mapper.DentistMapper;
import edu.sehs4701.hkdc.core.payload.response.DentistResponseDto;
import edu.sehs4701.hkdc.core.repository.DentistRepository;
import edu.sehs4701.hkdc.core.service.DentistService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DentistServiceImpl implements DentistService {

    @NonNull
    private final DentistRepository dentistRepository;

    public List<DentistResponseDto> listAll() {
        return dentistRepository.findAllWithSchedules()
                .stream()
                .map(DentistMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DentistResponseDto getById(Long id) {
        return dentistRepository.findByIdWithSchedules(id)
                .map(DentistMapper::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Dentist not found: " + id));
    }
}
