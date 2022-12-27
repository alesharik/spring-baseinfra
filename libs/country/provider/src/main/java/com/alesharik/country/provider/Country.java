package com.alesharik.country.provider;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

/**
 * Country
 */
@Schema(description = "Country")
public record Country(
        @NonNull
        @Schema(description = "Alpha-2 Code")
        String code,
        @NonNull
        @Schema(description = "Country name")
        String name,
        @NonNull
        @Schema(description = "Country icon url")
        String iconUrl
) {
}
