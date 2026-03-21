/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.misc.watchhistory

import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patches.music.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import com.extenre.patches.music.utils.patch.PatchList.WATCH_HISTORY
import com.extenre.patches.music.utils.settings.CategoryType
import com.extenre.patches.music.utils.settings.addPreferenceWithIntent
import com.extenre.patches.music.utils.settings.settingsPatch
import com.extenre.patches.shared.trackingurlhook.hookWatchHistory
import com.extenre.patches.shared.trackingurlhook.trackingUrlHookPatch

@Suppress("unused")
val watchHistoryPatch = bytecodePatch(
    WATCH_HISTORY.title,
    WATCH_HISTORY.summary,
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(
        settingsPatch,
        trackingUrlHookPatch,
    )

    execute {
        hookWatchHistory()

        addPreferenceWithIntent(
            CategoryType.MISC,
            "extenre_watch_history_type"
        )
    }

}