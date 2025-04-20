package edu.sehs4701.hkdc.core.service;

import edu.sehs4701.hkdc.core.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    User getCurrentUser();

}