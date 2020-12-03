package com.poland.college.controller;

import com.poland.college.dto.request.SignupRequest;
import com.poland.college.dto.request.UserRequest;
import com.poland.college.dto.response.MessageResponse;
import com.poland.college.dto.response.UserResponse;
import com.poland.college.service.AuthService;
import com.poland.college.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    private final UserService userService;

    @GetMapping("get")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUsers(Pageable pageable){
        return ResponseEntity.ok().body(userService.getUser(pageable));
    }



    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (authService.existEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok().body(userService.saveUser(userRequest));
    }

    @GetMapping("/responsibility")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> getResponsibility(@RequestHeader (name="Authorization") String token){
        System.out.println(token);
        return ResponseEntity.ok().body("dasda");
    }
}
