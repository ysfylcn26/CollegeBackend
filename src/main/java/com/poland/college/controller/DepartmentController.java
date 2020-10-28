package com.poland.college.controller;

import com.poland.college.dto.request.DepartmentRequest;
import com.poland.college.dto.request.InfoRequest;
import com.poland.college.dto.response.DepartmentResponse;
import com.poland.college.dto.response.InfoResponse;
import com.poland.college.dto.response.ResponseDegree;
import com.poland.college.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/add")
    private ResponseEntity<List<DepartmentResponse>> addDepartment(@Valid @RequestBody DepartmentRequest departmentRequest){
        return ResponseEntity.ok().body(departmentService.addDepartment(departmentRequest));
    }

    @GetMapping("/{college}")
    private ResponseEntity<List<DepartmentResponse>> getDepartment(@PathVariable("college") String college){
        return ResponseEntity.ok().body(departmentService.getDepartment(college));
    }

    @GetMapping("/degrees")
    private ResponseEntity<ResponseDegree> getDegrees() {
        return ResponseEntity.ok().body(departmentService.getDegrees());
    }

    @DeleteMapping("/{department}/{college}")
    private ResponseEntity<List<DepartmentResponse>> deleteDepartment(@PathVariable("department") String department, @PathVariable("college") String college){
        return ResponseEntity.ok().body(departmentService.delete(department, college));
    }

    @PutMapping("/{oldDepartment}")
    private ResponseEntity<List<DepartmentResponse>> update(@Valid @RequestBody DepartmentRequest departmentRequest,
                                                    @PathVariable("oldDepartment") String oldDepartment){
        return ResponseEntity.ok().body(departmentService.update(oldDepartment, departmentRequest));
    }

    @PostMapping("/info")
    private ResponseEntity<InfoResponse> saveInfo(@Valid @RequestBody InfoRequest infoRequest){
        return ResponseEntity.ok().body(departmentService.saveInfo(infoRequest));
    }

    @GetMapping("/info/{id}")
    private ResponseEntity<InfoResponse> getInfo(@PathVariable("id") String id){
        return ResponseEntity.ok().body(departmentService.getInfo(id));
    }

    @PostMapping("/comment")
    private ResponseEntity<InfoResponse> saveComment(@Valid @RequestBody InfoRequest infoRequest){
        return ResponseEntity.ok().body(departmentService.saveComment(infoRequest));
    }

    @GetMapping("/comment/{id}")
    private ResponseEntity<InfoResponse> getComment(@PathVariable("id") String id){
        return ResponseEntity.ok().body(departmentService.getComment(id));
    }

}
