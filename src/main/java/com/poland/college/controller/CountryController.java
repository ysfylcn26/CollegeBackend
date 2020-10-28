package com.poland.college.controller;

import com.poland.college.dto.request.CountryRequest;
import com.poland.college.dto.response.CountryResponse;
import com.poland.college.dto.response.MessageResponse;
import com.poland.college.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @PostMapping("/add")
    private ResponseEntity<?> addCountry(@Valid @RequestBody CountryRequest countryRequest) {
        if (countryService.existCountry(countryRequest.getCountry())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Country is already added!"));
        }
        return ResponseEntity.ok().body(countryService.addCountry(countryRequest));
    }

    @GetMapping("/get")
    private ResponseEntity<List<CountryResponse>> getAll() {
        return ResponseEntity.ok().body(countryService.getAll());
    }

    @DeleteMapping("/{uuid}")
    private ResponseEntity<?> deleteCountry(@PathVariable(name = "uuid") String uuid) {
        if (!countryService.existCountryByUuid(uuid)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: There isnt this country!"));
        }
        return ResponseEntity.ok().body(countryService.delete(uuid));
    }

    @PutMapping("/{uuid}")
    private ResponseEntity<?> updateCountry(@Valid @RequestBody CountryRequest countryRequest
            , @PathVariable(name = "uuid") String uuid) {
        return ResponseEntity.ok().body(countryService.update(uuid, countryRequest.getCountry()));
    }
}
