package edu.sehs4701.hkdc.core.payload.response;

import edu.sehs4701.hkdc.core.type.Gender;
import edu.sehs4701.hkdc.core.type.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
public class AuthResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String address;


    private Role role;

}
