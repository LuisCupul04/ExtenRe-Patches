/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.shared.settings

import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patches.shared.extension.Constants.EXTENSION_THEME_UTILS_CLASS_DESCRIPTOR
import com.extenre.util.findMethodsOrThrow
import com.extenre.util.returnEarly

private const val THEME_FOREGROUND_COLOR = "@color/yt_white1"
private const val THEME_BACKGROUND_COLOR = "@color/yt_black3"

val baseSettingsPatch = bytecodePatch(
    description = "baseSettingsPatch"
) {
    execute {
        findMethodsOrThrow(EXTENSION_THEME_UTILS_CLASS_DESCRIPTOR).apply {
            find { method -> method.name == "getThemeLightColorResourceName" }
                ?.returnEarly(THEME_FOREGROUND_COLOR)
            find { method -> method.name == "getThemeDarkColorResourceName" }
                ?.returnEarly(THEME_BACKGROUND_COLOR)
        }
    }
}


