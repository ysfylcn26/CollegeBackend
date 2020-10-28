package com.poland.college.repository;

import com.poland.college.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface CollegeRepository extends JpaRepository<College, Integer> {
    Optional<List<College>> findByCountry_Uuid(String uuid);

    boolean existsByNameAndCountry_Uuid(String name, String country);

    Optional<College> findByUuidAndCountry_Uuid(String collegeUuid, String countryUuid);

    Integer deleteByUuidAndCountry_Uuid(String CollegeUuid, String CountryUuid);

    College findByUuid(String uuid);

}
