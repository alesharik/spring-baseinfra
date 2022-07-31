package com.alesharik.appversion.service.service;

import com.alesharik.appversion.service.domain.AppVersion;

import java.util.List;

public interface VersionInfoProvider {
    List<AppVersion> getVersions(String client, String branch);
}
