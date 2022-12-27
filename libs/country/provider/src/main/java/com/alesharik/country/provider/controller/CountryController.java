package com.alesharik.country.provider.controller;

import com.alesharik.country.provider.Country;
import com.alesharik.country.provider.CountryProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "country")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryController {
    private final CountryProvider countryProvider;

    @GetMapping
    @Operation(
            operationId = "getCountries",
            summary = "Get all countries"
    )
    public List<Country> getCountries() {
        return countryProvider.getCountries();
    }
}
