package com.poland.college.dto.response;

import com.poland.college.entity.enums.Degree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDegree {
    private List<Degree> degrees;
}
