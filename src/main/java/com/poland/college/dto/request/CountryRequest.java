package com.poland.college.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryRequest {

    @NotBlank
    @Size(max = 50)
    private String country;


}
