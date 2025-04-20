package edu.sehs4701.hkdc.core.payload.request;

import edu.sehs4701.hkdc.core.type.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
public class SignupRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    // new code added to provide gender, DOB and address and pass it to the method to register new patient

    private Gender gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String address;

}
