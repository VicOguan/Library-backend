package com.tutorial.study.initializer;

import com.tutorial.study.entity.AppUser;
import com.tutorial.study.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){
        if (userRepository.findByUserName("admin").isEmpty()){
            AppUser admin = new AppUser();
            admin.setUserName("admin");
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setRole("ADMIN");

            userRepository.save(admin);

            System.out.println("Initial admin account created");
        }
    }
}
