package com.tutorial.study.service;

import com.tutorial.study.dto.RegisterRequest;
import com.tutorial.study.entity.AppUser;
import com.tutorial.study.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request){
        AppUser appUser = new AppUser();

        appUser.setUserName(request.getUsername());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));

        appUser.setRole("USER");

        userRepository.save(appUser);
    }


}
