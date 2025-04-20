package edu.sehs4701.hkdc.core.service;

import edu.sehs4701.hkdc.core.payload.response.ClinicDetailResponseDto;
import edu.sehs4701.hkdc.core.payload.response.ClinicResponseDto;

import java.util.List;
import java.util.Optional;

public interface ClinicService {

    Optional<ClinicDetailResponseDto> getClinicDetail(Long id);

    List<ClinicResponseDto> getAllClinics();
}
