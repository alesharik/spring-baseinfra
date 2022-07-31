package com.alesharik.appversion.service;

import com.alesharik.appversion.service.domain.AppVersion;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties("versioning.app")
public class AppVersionProperties {
    private Map<String, Client> clients;

    public record Client(
            Map<String, List<AppVersion>> branches
    ) {
    }
}
