package edu.sehs4701.hkdc.core.service.impl;

import edu.sehs4701.hkdc.core.mapper.UserMapper;
import edu.sehs4701.hkdc.core.model.Patient;
import edu.sehs4701.hkdc.core.model.User;
import edu.sehs4701.hkdc.core.payload.request.SigninRequest;
import edu.sehs4701.hkdc.core.payload.request.SignupRequest;
import edu.sehs4701.hkdc.core.payload.response.AuthResponseDto;
import edu.sehs4701.hkdc.core.repository.PatientRepository;
import edu.sehs4701.hkdc.core.repository.UserRepository;
import edu.sehs4701.hkdc.core.service.AuthService;
import edu.sehs4701.hkdc.core.type.Role;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final AuthenticationManager authenticationManager;

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final UserMapper userMapper;

    @NonNull
    private final PatientRepository patientRepository;

    @Override
    public AuthResponseDto signup(SignupRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhoneNumber());
        user.setRole(Role.PATIENT);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setGender(request.getGender());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setAddress(request.getAddress());

        patientRepository.save(patient);

        return userMapper.userToAuthResponseDto(user);
    }

    @Override
    public AuthResponseDto signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User login failed!"));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return userMapper.userToAuthResponseDto(user);
    }

}
