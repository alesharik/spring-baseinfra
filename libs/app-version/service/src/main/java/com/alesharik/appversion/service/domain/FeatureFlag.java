package com.alesharik.appversion.service.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

/**
 * Feature-flag
 */
public record FeatureFlag(
        @NonNull
        @Schema(description = "Feature flag name")
        String name,
        @NonNull
        @Schema(description = "Is feature flag enabled?")
        boolean enabled
) {
}
