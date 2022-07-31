package com.alesharik.appversion.service.domain;

import java.util.List;

public record AppVersion(
        int code,
        String name,
        boolean minor,
        boolean major,
        List<FeatureFlag> featureFlags
) {
}
