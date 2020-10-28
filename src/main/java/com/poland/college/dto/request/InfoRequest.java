package com.poland.college.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoRequest {
    @NotBlank
    private String id;
    private Double fee;
    private String info;
    private LocalDate start;
    private LocalDate end;
}
