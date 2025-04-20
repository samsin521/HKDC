package edu.sehs4701.hkdc.core.service;

import edu.sehs4701.hkdc.core.payload.response.DentistResponseDto;

import java.util.List;

public interface DentistService {

    DentistResponseDto getById(Long id);

    List<DentistResponseDto> listAll();
}
