/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.reddit.utils.resourceid

import app.morphe.patcher.patch.resourcePatch
import app.morphe.patches.shared.mapping.ResourceType.STRING
import app.morphe.patches.shared.mapping.getResourceId
import app.morphe.patches.shared.mapping.resourceMappingPatch

var nsfwDialogTitle = -1L
    private set

internal val sharedResourceIdPatch = resourcePatch(
    description = "sharedResourceIdPatch"
) {
    dependsOn(resourceMappingPatch)

    execute {
        nsfwDialogTitle = getResourceId(STRING, "nsfw_dialog_title")
    }
}