/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.misc.thumbnails

import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patches.music.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import com.extenre.patches.music.utils.patch.PatchList.BYPASS_IMAGE_REGION_RESTRICTIONS
import com.extenre.patches.music.utils.settings.CategoryType
import com.extenre.patches.music.utils.settings.ResourceUtils.updatePatchStatus
import com.extenre.patches.music.utils.settings.addSwitchPreference
import com.extenre.patches.music.utils.settings.settingsPatch
import com.extenre.patches.shared.imageurl.addImageUrlHook
import com.extenre.patches.shared.imageurl.cronetImageUrlHookPatch

@Suppress("unused")
val bypassImageRegionRestrictionsPatch = bytecodePatch(
    BYPASS_IMAGE_REGION_RESTRICTIONS.title,
    BYPASS_IMAGE_REGION_RESTRICTIONS.summary,
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(
        settingsPatch,
        cronetImageUrlHookPatch(false)
    )

    execute {
        addImageUrlHook()

        addSwitchPreference(
            CategoryType.MISC,
            "extenre_bypass_image_region_restrictions",
            "false"
        )

        updatePatchStatus(BYPASS_IMAGE_REGION_RESTRICTIONS)

    }
}