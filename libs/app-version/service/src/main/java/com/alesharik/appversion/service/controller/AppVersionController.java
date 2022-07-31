package com.alesharik.appversion.service.controller;

import com.alesharik.appversion.service.domain.VersionResult;
import com.alesharik.appversion.service.AppVersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "app-version")
@RestController
@RequestMapping("/api/v2/app-version/{client}")
@RequiredArgsConstructor
public class AppVersionController {
    private final AppVersionService appVersionService;

    @GetMapping
    @Operation(description = "Get version action", operationId = "getVersionAction")
    public VersionResult getVersionAction(@PathVariable("client") @Parameter(name = "client", in = ParameterIn.PATH, description = "Client ID") String client,
                                          @RequestParam("version") @Parameter(description = "Client version code", required = true) int version,
                                          @RequestParam("branch") @Parameter(description = "Client branch", required = true) String branch,
                                          @RequestParam(value = "id", required = false) @Parameter(description = "Device ID") String id) {
        return appVersionService.getVersionAction(client, branch, version);
    }
}
