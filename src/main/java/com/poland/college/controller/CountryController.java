package com.poland.college.controller;

import com.poland.college.dto.request.CountryRequest;
import com.poland.college.dto.response.CountryResponse;
import com.poland.college.dto.response.MessageResponse;
import com.poland.college.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> addCountry(@Valid @RequestBody CountryRequest countryRequest) {
        if (countryService.existCountry(countryRequest.getCountry())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Country is already added!"));
        }
        return ResponseEntity.ok().body(countryService.addCountry(countryRequest));
    }

    @GetMapping("/get")
    public ResponseEntity<List<CountryResponse>> getAll() {
        return ResponseEntity.ok().body(countryService.getAll());
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> deleteCountry(@PathVariable(name = "uuid") String uuid) {
        if (!countryService.existCountryByUuid(uuid)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: There isnt this country!"));
        }
        return ResponseEntity.ok().body(countryService.delete(uuid));
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> updateCountry(@Valid @RequestBody CountryRequest countryRequest
            , @PathVariable(name = "uuid") String uuid) {
        return ResponseEntity.ok().body(countryService.update(uuid, countryRequest.getCountry()));
    }

}
