package com.poland.college.service;

import com.poland.college.dto.request.UserRequest;
import com.poland.college.dto.response.CountryResponse;
import com.poland.college.dto.response.UserResponse;
import com.poland.college.entity.Country;
import com.poland.college.entity.Role;
import com.poland.college.entity.User;
import com.poland.college.entity.enums.ERole;
import com.poland.college.repository.CountryRepository;
import com.poland.college.repository.RoleRepository;
import com.poland.college.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CountryRepository countryRepository;
    private final PasswordEncoder encoder;

    public Page<UserResponse> getUser(Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        Page<UserResponse> userResponses = users.map(user -> {
           CountryResponse countryResponse = null;
           if(user.getResponsibility() != null){
               countryResponse = new CountryResponse();
               Country country = countryRepository.findByUuid(user.getResponsibility()).get();
               countryResponse.setId(country.getUuid());
               countryResponse.setName(country.getName());
           }
           return new UserResponse().builder().userId(user.getUuid()).username(user.getEmail()).role(user.getRole().getName().name()).country(countryResponse).build();
        });
        return userResponses;
    }

    @Transactional
    public UserResponse saveUser(UserRequest userRequest){
        User user = userRepository.findByUuid(userRequest.getUserId()).orElseThrow( () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot find this user");
        });
        if(!(user.getRole().getName() == userRequest.getRole())){
            Role role = roleRepository.findByName(userRequest.getRole()).orElseThrow( () -> {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot this role");
            });
            user.setRole(role);
        }
        CountryResponse countryResponse = null;
        if ((userRequest.getRole() == ERole.ROLE_ADMIN)){
            if(userRequest.getCountry() == null){
                user.setResponsibility(null);
            }else if(userRequest.getCountry().getId() != null){
                Country country = countryRepository.findByUuid(userRequest.getCountry().getId()).orElseThrow( () -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot find country");
                });
                countryResponse = new CountryResponse().builder().id(country.getUuid()).name(country.getName()).build();
                user.setResponsibility(country.getUuid());
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot find country");
            }
        }
        if(userRequest.getPass() != null && !userRequest.getPass().isEmpty()){
            user.setPass(encoder.encode(userRequest.getPass()));
        }
        userRepository.saveAndFlush(user);
        UserResponse userResponse = new UserResponse().builder().userId(user.getUuid()).
                country(countryResponse).
                role(user.getRole().getName().name()).username(user.getEmail()).build();
        return userResponse;
    }

}
