/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.misc.watchhistory

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.trackingurlhook.hookWatchHistory
import app.morphe.patches.shared.trackingurlhook.trackingUrlHookPatch
import app.morphe.patches.youtube.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.morphe.patches.youtube.utils.patch.PatchList.WATCH_HISTORY
import app.morphe.patches.youtube.utils.settings.ResourceUtils.addPreference
import app.morphe.patches.youtube.utils.settings.settingsPatch

@Suppress("unused")
val watchHistoryPatch = bytecodePatch(
    name = WATCH_HISTORY.key,
    description = "${WATCH_HISTORY.title}: ${WATCH_HISTORY.summary}",
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(
        settingsPatch,
        trackingUrlHookPatch,
    )

    execute {

        hookWatchHistory()

        // region add settings

        addPreference(
            arrayOf(
                "SETTINGS: WATCH_HISTORY"
            ),
            WATCH_HISTORY
        )

        // endregion

    }
}
