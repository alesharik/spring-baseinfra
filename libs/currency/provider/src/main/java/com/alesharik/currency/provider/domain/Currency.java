package com.alesharik.currency.provider.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

public record Currency(
        @NonNull
        @Schema(description = "Currency symbol")
        String symbol,
        @NonNull
        @Schema(description = "Currency code")
        String code
) {
}
