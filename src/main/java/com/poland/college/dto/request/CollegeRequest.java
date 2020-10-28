package com.poland.college.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollegeRequest {

    @NotBlank
    private String country;

    @NotBlank
    @Size(max = 300)
    private String college;
}
