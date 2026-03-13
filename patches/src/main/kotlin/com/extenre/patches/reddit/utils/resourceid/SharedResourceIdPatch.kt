/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.reddit.utils.resourceid

import com.extenre.patcher.patch.resourcePatch
import com.extenre.patches.shared.mapping.ResourceType.STRING
import com.extenre.patches.shared.mapping.getResourceId
import com.extenre.patches.shared.mapping.resourceMappingPatch

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