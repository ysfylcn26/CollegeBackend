package com.poland.college.dto.request;

import com.poland.college.entity.enums.Degree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {

    @NotBlank
    private String college;

    @NotBlank
    @Size(max = 300)
    private String department;

    private Degree degree;

}
