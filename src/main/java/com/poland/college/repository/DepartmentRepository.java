package com.poland.college.repository;

import com.poland.college.entity.Department;
import com.poland.college.entity.enums.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    boolean existsByUuid(String uuid);

    boolean existsByNameAndDegreeAndColleges_Uuid(String department, Degree degree, String college);

    Optional<List<Department>> findByColleges_Uuid(String college);

    void deleteByUuid(String uuid);

    Optional<Department> findByUuid(String uuid);
}
