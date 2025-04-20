package edu.sehs4701.hkdc.core.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SigninRequest {

    private String email;

    private String password;

}
