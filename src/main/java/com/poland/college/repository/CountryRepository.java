package com.poland.college.repository;

import com.poland.college.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean existsByName(String name);

    boolean existsByUuid(String uuid);

    Integer deleteByUuid(String uuid);

    Optional<Country> findByUuid(String uuid);

}
