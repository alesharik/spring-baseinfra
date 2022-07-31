package com.alesharik.appversion.service.domain;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Acton for current version
 */
@Schema(description = "Acton for current version")
public enum VersionAction {
    /**
     * Do nothing
     */
    @Schema(description = "Do nothing")
    NONE,

    /**
     * Warn user about update possibility
     */
    @Schema(description = "Warn user about update possibility")
    UPGRADE_WARNING,

    /**
     * Block app until updated
     */
    @Schema(description = "Block app until updated")
    UPGRADE_REQUIRED
}
