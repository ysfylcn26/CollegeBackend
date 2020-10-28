package com.poland.college.dto.response;

import com.poland.college.entity.enums.Degree;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {
    private String id;
    private String name;
    private Degree degree;
}
