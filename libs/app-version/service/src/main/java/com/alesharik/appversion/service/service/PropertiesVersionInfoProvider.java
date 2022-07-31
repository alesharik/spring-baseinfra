package com.alesharik.appversion.service.service;

import com.alesharik.appversion.service.AppVersionProperties;
import com.alesharik.appversion.service.domain.AppVersion;
import com.alesharik.appversion.service.exception.BranchNotFoundException;
import com.alesharik.appversion.service.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertiesVersionInfoProvider implements VersionInfoProvider {
    private final AppVersionProperties properties;

    @Override
    public List<AppVersion> getVersions(String client, String branch) {
        var cl = properties.getClients().get(client);
        if (cl == null)
            throw new ClientNotFoundException();
        var br = cl.branches().get(branch);
        if (br == null)
            throw new BranchNotFoundException();
        return br;
    }
}
