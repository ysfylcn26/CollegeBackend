package com.poland.college.service;

import com.poland.college.dto.request.CountryRequest;
import com.poland.college.dto.response.CountryResponse;
import com.poland.college.entity.Country;
import com.poland.college.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional
    public List<CountryResponse> addCountry(CountryRequest countryRequest) {
        Country country = new Country();
        country.setName(countryRequest.getCountry());
        countryRepository.save(country);
        List<CountryResponse> countries = countryRepository.findAll().stream().map(data -> {
            return new CountryResponse().builder().id(data.getUuid().toString()).name(data.getName()).build();
        }).collect(Collectors.toList());
        return countries;
    }

    @Transactional
    public List<CountryResponse> getAll() {
        List<CountryResponse> countries = countryRepository.findAll().stream().map(data -> {
            return new CountryResponse().builder().id(data.getUuid()).name(data.getName()).build();
        }).collect(Collectors.toList());
        return countries;
    }

    @Transactional
    public List<CountryResponse> delete(String uuid) {
        countryRepository.deleteByUuid(uuid);
        List<CountryResponse> countries = countryRepository.findAll().stream().map(data -> {
            return new CountryResponse().builder().id(data.getUuid().toString()).name(data.getName()).build();
        }).collect(Collectors.toList());
        return countries;
    }

    @Transactional
    public List<CountryResponse> update(String uuid, String newCountry) {
        Country country = countryRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not fount country");
        });
        if(existCountry(newCountry)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already there is this name");
        }
        country.setName(newCountry);
        countryRepository.save(country);
        List<CountryResponse> countries = countryRepository.findAll().stream().map(data -> {
            return new CountryResponse().builder().id(data.getUuid()).name(data.getName()).build();
        }).collect(Collectors.toList());
        return countries;
    }

    public boolean existCountry(String name) {
        return countryRepository.existsByName(name);
    }

    public boolean existCountryByUuid(String uuid) {
        return countryRepository.existsByUuid(uuid);
    }
}
