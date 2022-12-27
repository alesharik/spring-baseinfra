package com.alesharik.currency.provider.dto;

import com.alesharik.currency.provider.domain.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

import java.util.List;

@Schema(description = "Currency list", name = "CurrencyList")
public record CurrencyListDto(
        @NonNull
        @Schema(description = "Currency list")
        List<Currency> currencies
) {
}
