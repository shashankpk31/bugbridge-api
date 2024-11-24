package com.shashankpk.bug_bridge.config;

import com.shashankpk.bug_bridge.services.JwtUserDetailsService;
import com.shashankpk.bug_bridge.repositories.UserRepository;
import com.shashankpk.bug_bridge.repositories.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUserDetailsService jwtUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return new JwtUserDetailsService(userRepository, roleRepository, passwordEncoder);
    }
}
