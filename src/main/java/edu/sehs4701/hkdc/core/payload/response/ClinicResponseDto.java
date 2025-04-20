package edu.sehs4701.hkdc.core.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicResponseDto {
    private Long id;
    private String name;
    private String address;
    private String openHours;
}
