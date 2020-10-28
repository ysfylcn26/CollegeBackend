package com.poland.college.service;

import com.poland.college.dto.request.CollegeRequest;
import com.poland.college.dto.response.CollegeResponse;
import com.poland.college.entity.College;
import com.poland.college.entity.Country;
import com.poland.college.repository.CollegeRepository;
import com.poland.college.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollegeService {

    private final CollegeRepository collegeRepository;
    private final CountryRepository countryRepository;

    @Transactional
    public List<CollegeResponse> addCollege(CollegeRequest collegeRequest) {
        Country country = countryRepository.findByUuid(collegeRequest.getCountry())
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There isnt country");
                });
        if (existCollege(collegeRequest))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is this college");
        College college = new College();
        college.setName(collegeRequest.getCollege());
        college.setCountry(country);
        collegeRepository.save(college);

        List<College> colleges = this.collegeRepository.findByCountry_Uuid(collegeRequest.getCountry()).orElseGet(() -> new ArrayList<>());
        List<CollegeResponse> collegesDto = colleges.stream().map(data -> new CollegeResponse().builder().id(data.getUuid()).name(data.getName()).build())
                .collect(Collectors.toList());
        return collegesDto;

    }

    @Transactional
    public List<CollegeResponse> deleteCollege(String country, String college) {
        this.collegeRepository.deleteByUuidAndCountry_Uuid(college, country);
        List<College> collge = this.collegeRepository.findByCountry_Uuid(country).orElseGet(() -> new ArrayList<>());
        List<CollegeResponse> collges = collge.stream().map(data -> new CollegeResponse().builder().id(data.getUuid()).name(data.getName()).build())
                .collect(Collectors.toList());
        return collges;
    }

    @Transactional
    public List<CollegeResponse> getColleges(String country) {
        List<College> collge = this.collegeRepository.findByCountry_Uuid(country).orElseGet(() -> new ArrayList<>());
        List<CollegeResponse> collges = collge.stream().map(data -> new CollegeResponse().builder().id(data.getUuid()).name(data.getName()).build())
                .collect(Collectors.toList());
        return collges;
    }

    @Transactional
    public List<CollegeResponse> updateCollege(String oldCollege, CollegeRequest collegeRequest) {
        College college = collegeRepository.findByUuidAndCountry_Uuid(oldCollege, collegeRequest.getCountry())
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find college to update");
                });
        if(existCollege(collegeRequest)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already there is this college");
        }
        college.setName(collegeRequest.getCollege());
        collegeRepository.save(college);
        List<CollegeResponse> collges = this.collegeRepository.findByCountry_Uuid(collegeRequest.getCountry()).get()
                .stream().map(data -> new CollegeResponse().builder().id(data.getUuid()).name(data.getName()).build())
                .collect(Collectors.toList());
        return collges;
    }

    @Transactional
    public boolean existCollege(CollegeRequest collegeRequest){
       return this.collegeRepository.existsByNameAndCountry_Uuid(collegeRequest.getCollege(), collegeRequest.getCountry());
    }
}
