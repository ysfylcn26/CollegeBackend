package com.poland.college.dto.request;

import com.poland.college.dto.response.CountryResponse;
import com.poland.college.entity.enums.ERole;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String username;

    private ERole role;

    private CountryResponse country;

    @Pattern(regexp = "^([A-Za-z0-9]{4,20}){1}$|^$", message = "Password not valid")
    private String pass;
}
