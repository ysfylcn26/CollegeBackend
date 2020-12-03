package com.poland.college.controller;

import com.poland.college.dto.request.DepartmentRequest;
import com.poland.college.dto.request.InfoRequest;
import com.poland.college.dto.response.DepartmentResponse;
import com.poland.college.dto.response.InfoResponse;
import com.poland.college.dto.response.ResponseDegree;
import com.poland.college.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<List<DepartmentResponse>> addDepartment(@Valid @RequestBody DepartmentRequest departmentRequest){
        return ResponseEntity.ok().body(departmentService.addDepartment(departmentRequest));
    }

    @GetMapping("/{college}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<List<DepartmentResponse>> getDepartment(@PathVariable("college") String college){
        return ResponseEntity.ok().body(departmentService.getDepartment(college));
    }

    @GetMapping("/degrees")
    public ResponseEntity<ResponseDegree> getDegrees() {
        return ResponseEntity.ok().body(departmentService.getDegrees());
    }

    @DeleteMapping("/{department}/{college}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<List<DepartmentResponse>> deleteDepartment(@PathVariable("department") String department, @PathVariable("college") String college){
        return ResponseEntity.ok().body(departmentService.delete(department, college));
    }

    @PutMapping("/{oldDepartment}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<List<DepartmentResponse>> update(@Valid @RequestBody DepartmentRequest departmentRequest,
                                                    @PathVariable("oldDepartment") String oldDepartment){
        return ResponseEntity.ok().body(departmentService.update(oldDepartment, departmentRequest));
    }

    @PostMapping("/info")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<InfoResponse> saveInfo(@Valid @RequestBody InfoRequest infoRequest){
        return ResponseEntity.ok().body(departmentService.saveInfo(infoRequest));
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<InfoResponse> getInfo(@PathVariable("id") String id){
        return ResponseEntity.ok().body(departmentService.getInfo(id));
    }

    @PostMapping("/comment")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<InfoResponse> saveComment(@Valid @RequestBody InfoRequest infoRequest){
        return ResponseEntity.ok().body(departmentService.saveComment(infoRequest));
    }

    @GetMapping("/comment/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<InfoResponse> getComment(@PathVariable("id") String id){
        return ResponseEntity.ok().body(departmentService.getComment(id));
    }

}
