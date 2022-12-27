package com.alesharik.currency.provider.controller;

import com.alesharik.currency.provider.CurrencyProvider;
import com.alesharik.currency.provider.dto.CurrencyListDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "currency")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currencies")
public class CurrencyController {
    private final CurrencyProvider currencyProvider;

    @GetMapping
    @Operation(
            operationId = "getCurrencies",
            summary = "Get all currencies"
    )
    public CurrencyListDto getCurrencies() {
        return new CurrencyListDto(currencyProvider.getCurrencies());
    }
}
