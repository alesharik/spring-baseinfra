package com.alesharik.appversion.service.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.NonNull;

import java.util.List;

public record VersionResult(
        @NonNull
        @Schema(description = "Action")
        VersionAction action,
        @Schema(description = "Next app version, if has any")
        String nextVersion,
        @NonNull
        @Schema(description = "App feature flags")
        List<FeatureFlag> featureFlags
) {
}
