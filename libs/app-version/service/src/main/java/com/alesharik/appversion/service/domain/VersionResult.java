package com.alesharik.appversion.service.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record VersionResult(
        @Schema(description = "Action", required = true)
        VersionAction action,
        @Schema(description = "Next app version, if has any")
        String nextVersion,
        @Schema(description = "App feature flags", required = true)
        List<FeatureFlag> featureFlags
) {
}
