package com.poland.college.controller;

import com.poland.college.dto.request.CollegeRequest;
import com.poland.college.dto.response.MessageResponse;
import com.poland.college.service.CollegeService;
import com.poland.college.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/college")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @PostMapping(value = "/add")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> addCollege(@Valid @RequestBody CollegeRequest collegeRequest){
        return ResponseEntity.ok().body(collegeService.addCollege(collegeRequest));
    }

    @GetMapping("/{country}")
    public ResponseEntity<?> getColleges(@PathVariable(name = "country") String country){
        return ResponseEntity.ok().body(collegeService.getColleges(country));
    }

    @DeleteMapping("/{country}/{college}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> deleteCollege(@PathVariable(name = "country") String country, @PathVariable(name = "college") String college){
        return ResponseEntity.ok().body(collegeService.deleteCollege(country, college));
    }

    @PutMapping("/{collegeUuid}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<?> updateCollege(@PathVariable("collegeUuid") String collegeUuid,@Valid @RequestBody CollegeRequest collegeRequest){
        return ResponseEntity.ok().body(collegeService.updateCollege(collegeUuid, collegeRequest));
    }
}
