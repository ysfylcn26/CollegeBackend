package com.poland.college.controller;

import com.poland.college.dto.request.CollegeRequest;
import com.poland.college.dto.response.MessageResponse;
import com.poland.college.service.CollegeService;
import com.poland.college.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/college")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CollegeController {

    private final CollegeService collegeService;

    @PostMapping(value = "/add")
    private ResponseEntity<?> addCollege(@Valid @RequestBody CollegeRequest collegeRequest){
        return ResponseEntity.ok().body(collegeService.addCollege(collegeRequest));
    }

    @GetMapping("/{country}")
    private ResponseEntity<?> getColleges(@PathVariable(name = "country") String country){
        return ResponseEntity.ok().body(collegeService.getColleges(country));
    }

    @DeleteMapping("/{country}/{college}")
    private ResponseEntity<?> deleteCollege(@PathVariable(name = "country") String country, @PathVariable(name = "college") String college){
        return ResponseEntity.ok().body(collegeService.deleteCollege(country, college));
    }

    @PutMapping("/{collegeUuid}")
    private ResponseEntity<?> updateCollege(@PathVariable("collegeUuid") String collegeUuid,@Valid @RequestBody CollegeRequest collegeRequest){
        return ResponseEntity.ok().body(collegeService.updateCollege(collegeUuid, collegeRequest));
    }
}
