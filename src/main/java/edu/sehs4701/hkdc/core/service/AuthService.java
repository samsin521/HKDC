package edu.sehs4701.hkdc.core.service;

import edu.sehs4701.hkdc.core.payload.request.SigninRequest;
import edu.sehs4701.hkdc.core.payload.request.SignupRequest;
import edu.sehs4701.hkdc.core.payload.response.AuthResponseDto;

public interface AuthService {

    AuthResponseDto signup(SignupRequest request);

    AuthResponseDto signin(SigninRequest request);
}

