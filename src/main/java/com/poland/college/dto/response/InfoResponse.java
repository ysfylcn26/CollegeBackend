package com.poland.college.dto.response;

import com.poland.college.entity.enums.Degree;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoResponse {
    private String id;
    private Double fee;
    private String info;
    private String name;
    private Degree degree;
    private LocalDate start;
    private LocalDate end;
}
