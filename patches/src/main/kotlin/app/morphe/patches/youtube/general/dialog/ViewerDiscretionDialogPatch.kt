/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.general.dialog

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.dialog.baseViewerDiscretionDialogPatch
import app.morphe.patches.youtube.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.morphe.patches.youtube.utils.extension.Constants.GENERAL_CLASS_DESCRIPTOR
import app.morphe.patches.youtube.utils.patch.PatchList.REMOVE_VIEWER_DISCRETION_DIALOG
import app.morphe.patches.youtube.utils.settings.ResourceUtils.addPreference
import app.morphe.patches.youtube.utils.settings.settingsPatch

@Suppress("unused")
val viewerDiscretionDialogPatch = bytecodePatch(
    name = REMOVE_VIEWER_DISCRETION_DIALOG.key,
    description = "${REMOVE_VIEWER_DISCRETION_DIALOG.title}: ${REMOVE_VIEWER_DISCRETION_DIALOG.summary}",
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(
        baseViewerDiscretionDialogPatch(
            GENERAL_CLASS_DESCRIPTOR,
            true
        ),
        settingsPatch,
    )

    execute {

        // region add settings

        addPreference(
            arrayOf(
                "PREFERENCE_SCREEN: GENERAL",
                "SETTINGS: REMOVE_VIEWER_DISCRETION_DIALOG"
            ),
            REMOVE_VIEWER_DISCRETION_DIALOG
        )

        // endregion

    }
}
