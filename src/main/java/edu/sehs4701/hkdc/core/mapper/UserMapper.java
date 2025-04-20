package edu.sehs4701.hkdc.core.mapper;

import edu.sehs4701.hkdc.core.model.User;
import edu.sehs4701.hkdc.core.payload.response.AuthResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AuthResponseDto userToAuthResponseDto(User user) {
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setId(user.getId());
        authResponseDto.setFirstName(user.getFirstName());
        authResponseDto.setLastName(user.getLastName());
        authResponseDto.setEmail(user.getEmail());
        authResponseDto.setPhone(user.getPhone());
        authResponseDto.setRole(user.getRole());
        return authResponseDto;
    }

}
