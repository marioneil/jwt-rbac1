package com.weather.rbac1.controller;

import com.weather.rbac1.dto.UserLogin;
import com.weather.rbac1.dto.UserSignUp;
import com.weather.rbac1.entity.AppUser;
import com.weather.rbac1.entity.UserRole;
import com.weather.rbac1.repo.AppUserRepo;
import com.weather.rbac1.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppUserRepo userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtility jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLogin loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserSignUp signUpRequest) {

        // Create new user's account
        AppUser user = new AppUser(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<UserRole> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    roles.add(new UserRole(1, UserRole.ERole.ROLE_ADMIN));
                    break;
                case "mod":
                    roles.add(new UserRole(2, UserRole.ERole.ROLE_MODERATOR));
                    break;
                case "prof":
                    roles.add(new UserRole(3, UserRole.ERole.ROLE_PROF));
                    break;
                case "hod":
                    roles.add(new UserRole(4, UserRole.ERole.ROLE_HOD));
                    break;
                default:
                    roles.add(new UserRole(5, UserRole.ERole.ROLE_USER));
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}