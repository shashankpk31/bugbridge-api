package com.shashankpk.bug_bridge.controllers;



import com.shashankpk.bug_bridge.dto.ApiResponse;
import com.shashankpk.bug_bridge.models.User;
import com.shashankpk.bug_bridge.security.JwtTokenUtil;
import com.shashankpk.bug_bridge.services.JwtUserDetailsService;
import com.shashankpk.bug_bridge.dto.JwtRequest;
import com.shashankpk.bug_bridge.dto.JwtResponse;
import com.shashankpk.bug_bridge.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserDTO user) {
        user.setRoles(Set.of("ROLE_ADMIN"));
        userDetailsService.save(user);
        return ResponseEntity.ok(new ApiResponse("Admin registered successfully"));
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) {
        user.setRoles(Set.of("ROLE_USER"));
        userDetailsService.save(user);
        return ResponseEntity.ok(new ApiResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

