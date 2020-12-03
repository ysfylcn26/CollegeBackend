package com.poland.college.service;

import com.poland.college.dto.request.LoginRequest;
import com.poland.college.dto.request.SignupRequest;
import com.poland.college.dto.response.JwtResponse;
import com.poland.college.dto.response.MessageResponse;
import com.poland.college.entity.Role;
import com.poland.college.entity.User;
import com.poland.college.entity.enums.ERole;
import com.poland.college.repository.RoleRepository;
import com.poland.college.repository.UserRepository;
import com.poland.college.security.JwtUtils;
import com.poland.college.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Transactional
    public JwtResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPass()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt, null,
                userDetails.getEmail(),
                roles.get(0));
    }

    @Transactional
    public MessageResponse signUp(SignupRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setEmail(signUpRequest.getEmail());
        user.setPass(encoder.encode(signUpRequest.getPass()));

        Role role = roleRepository.findByName(ERole.valueOf(signUpRequest.getRole())).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "We cannot find this role");
        });

        user.setRole(role);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
