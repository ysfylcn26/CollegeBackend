package com.poland.college.service;

import com.poland.college.dto.request.DepartmentRequest;
import com.poland.college.dto.request.InfoRequest;
import com.poland.college.dto.response.DepartmentResponse;
import com.poland.college.dto.response.InfoResponse;
import com.poland.college.dto.response.ResponseDegree;
import com.poland.college.entity.Department;
import com.poland.college.entity.enums.Degree;
import com.poland.college.repository.CollegeRepository;
import com.poland.college.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final CollegeRepository collegeRepository;

    public ResponseDegree getDegrees() {
        List<Degree> degrees = Arrays.asList(Degree.values());
        return new ResponseDegree(degrees);
    }

    @Transactional
    public List<DepartmentResponse> addDepartment(DepartmentRequest departmentRequest) {
        if (existDepartment(departmentRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already there is this department");
        }
        Department department = new Department();
        department.setName(departmentRequest.getDepartment());
        department.setDegree(departmentRequest.getDegree());
        department.setColleges(collegeRepository.findByUuid(departmentRequest.getCollege()));
        departmentRepository.save(department);
        List<Department> departments = departmentRepository.findByColleges_Uuid(departmentRequest.getCollege()).orElseGet(() -> new ArrayList<>());
        List<DepartmentResponse> departmentResponses = departments.stream().map(data -> {
            return new DepartmentResponse().builder().id(data.getUuid()).name(data.getName()).degree(data.getDegree()).build();
        }).collect(Collectors.toList());
        return departmentResponses;
    }

    @Transactional
    public List<DepartmentResponse> getDepartment(String college) {
        List<Department> departments = departmentRepository.findByColleges_Uuid(college).orElseGet(() -> new ArrayList<>());
        List<DepartmentResponse> departmentResponses = departments.stream().map(data -> {
            return new DepartmentResponse().builder().id(data.getUuid()).name(data.getName()).degree(data.getDegree()).build();
        }).collect(Collectors.toList());
        return departmentResponses;
    }

    @Transactional
    public List<DepartmentResponse> delete(String departmentUuid, String collegeUuid) {
        if (!departmentRepository.existsByUuid(departmentUuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There isnt this department");
        }
        departmentRepository.deleteByUuid(departmentUuid);
        List<Department> departments = departmentRepository.findByColleges_Uuid(collegeUuid).orElseGet(() -> new ArrayList<>());
        List<DepartmentResponse> departmentResponses = departments.stream().map(data -> {
            return new DepartmentResponse().builder().id(data.getUuid()).name(data.getName()).degree(data.getDegree()).build();
        }).collect(Collectors.toList());
        return departmentResponses;
    }

    @Transactional
    public List<DepartmentResponse> update(String oldDepartment, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findByUuid(oldDepartment).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There isnt this department");
        });
        if (existDepartment(departmentRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already there is this department");
        }
        department.setDegree(departmentRequest.getDegree());
        department.setName(departmentRequest.getDepartment());
        departmentRepository.save(department);
        List<Department> departments = departmentRepository.findByColleges_Uuid(departmentRequest.getCollege()).orElseGet(() -> new ArrayList<>());
        List<DepartmentResponse> departmentResponses = departments.stream().map(data -> {
                   return new DepartmentResponse().builder().id(data.getUuid()).name(data.getName()).degree(data.getDegree()).build();
                }).collect(Collectors.toList());
        return departmentResponses;
    }

    @Transactional
    public boolean existDepartment(DepartmentRequest departmentRequest) {
        return departmentRepository.existsByNameAndDegreeAndColleges_Uuid(departmentRequest.getDepartment(), departmentRequest.getDegree(), departmentRequest.getCollege());
    }

    @Transactional
    public InfoResponse saveInfo(InfoRequest infoRequest){
        Department department = departmentRepository.findByUuid(infoRequest.getId()).orElseThrow(() -> {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find this department");
        });
        department.setFee(infoRequest.getFee());
        department.setInfo(infoRequest.getInfo());
        department.setStart(infoRequest.getStart());
        department.setEnd(infoRequest.getEnd());
        departmentRepository.save(department);
        InfoResponse infoResponse = new InfoResponse().builder().id(department.getUuid()).name(department.getName()).
        degree(department.getDegree()).fee(department.getFee()).info(department.getInfo()).build();
        return infoResponse;
    }

    @Transactional
    public InfoResponse getInfo(String id){
        Department department = departmentRepository.findByUuid(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find this department");
        });
        InfoResponse infoResponse = new InfoResponse().builder().id(department.getUuid()).name(department.getName()).
                degree(department.getDegree()).fee(department.getFee()).info(department.getInfo())
                .start(department.getStart()).end(department.getEnd()).build();
        return infoResponse;
    }

    @Transactional
    public InfoResponse saveComment(InfoRequest infoRequest){
        Department department = departmentRepository.findByUuid(infoRequest.getId()).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find this department");
        });
        department.setComment(infoRequest.getInfo());
        departmentRepository.save(department);
        InfoResponse infoResponse = new InfoResponse().builder().id(department.getUuid()).name(department.getName()).
                degree(department.getDegree()).fee(department.getFee()).info(department.getInfo()).build();
        return infoResponse;
    }

    @Transactional
    public InfoResponse getComment(String id){
        Department department = departmentRepository.findByUuid(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot find this department");
        });
        InfoResponse infoResponse = new InfoResponse().builder().id(department.getUuid()).name(department.getName()).
                degree(department.getDegree()).info(department.getComment()).build();
        return infoResponse;
    }
}
