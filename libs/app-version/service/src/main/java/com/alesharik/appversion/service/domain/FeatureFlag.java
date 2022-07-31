package com.alesharik.appversion.service.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Feature-flag
 */
public record FeatureFlag(
        @Schema(description = "Feature flag name", required = true)
        String name,
        @Schema(description = "Is feature flag enabled?", required = true)
        boolean enabled
) {
}
