package com.alesharik.appversion.service;

import com.alesharik.appversion.service.domain.AppVersion;
import com.alesharik.appversion.service.domain.VersionAction;
import com.alesharik.appversion.service.domain.VersionResult;
import com.alesharik.appversion.service.service.VersionInfoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppVersionService {
    private final VersionInfoProvider versionInfoProvider;

    public VersionResult decide(int appVersion, @NonNull List<AppVersion> versions) {
        var featureFlags = versions.stream()
                .filter(version -> version.code() == appVersion)
                .findAny()
                .map(AppVersion::featureFlags)
                .orElse(List.of());
        List<AppVersion> followers = versions
                .stream()
                .filter(version -> version.code() > appVersion)
                .toList();
        for (AppVersion follower : followers)
            if (follower.major())
                return new VersionResult(
                        VersionAction.UPGRADE_REQUIRED,
                        followers.get(followers.size() - 1).name(),
                        featureFlags
                );
        for (AppVersion follower : followers)
            if (follower.minor())
                return new VersionResult(
                        VersionAction.UPGRADE_WARNING,
                        followers.get(followers.size() - 1).name(),
                        featureFlags
                );
        return new VersionResult(
                VersionAction.NONE,
                followers.isEmpty() ? null : followers.get(followers.size() - 1).name(),
                featureFlags
        );
    }

    public VersionResult getVersionAction(String client, String branch, int version) {
        var versions = versionInfoProvider.getVersions(client, branch);
        return decide(version, versions);
    }
}
