/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.music.layout.playeroverlay

import app.morphe.patcher.patch.resourcePatch
import app.morphe.patches.music.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.morphe.patches.music.utils.patch.PatchList.HIDE_PLAYER_OVERLAY_FILTER
import app.morphe.patches.music.utils.settings.ResourceUtils.updatePatchStatus
import app.morphe.util.removeOverlayBackground

@Suppress("unused")
val playerOverlayFilterPatch = resourcePatch(
    HIDE_PLAYER_OVERLAY_FILTER.title,
    HIDE_PLAYER_OVERLAY_FILTER.summary,
    use = false,
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    execute {
        removeOverlayBackground(
            arrayOf("music_controls_overlay.xml"),
            arrayOf("player_control_screen")
        )

        updatePatchStatus(HIDE_PLAYER_OVERLAY_FILTER)

    }
}

