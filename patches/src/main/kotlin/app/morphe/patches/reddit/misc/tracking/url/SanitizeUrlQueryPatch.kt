/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.reddit.misc.tracking.url

import app.morphe.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.smali.ExternalLabel
import app.morphe.patches.reddit.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.morphe.patches.reddit.utils.extension.Constants.PATCHES_PATH
import app.morphe.patches.reddit.utils.patch.PatchList.SANITIZE_SHARING_LINKS
import app.morphe.patches.reddit.utils.settings.settingsPatch
import app.morphe.patches.reddit.utils.settings.updatePatchStatus
import app.morphe.util.fingerprint.mutableMethodOrThrow

private const val SANITIZE_METHOD_DESCRIPTOR =
    "$PATCHES_PATH/SanitizeUrlQueryPatch;->stripQueryParameters()Z"

@Suppress("unused")
val sanitizeUrlQueryPatch = bytecodePatch(
    name = SANITIZE_SHARING_LINKS.key,
    description = "${SANITIZE_SHARING_LINKS.title}: ${SANITIZE_SHARING_LINKS.summary}",
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(settingsPatch)

    execute {
        shareLinkFormatterFingerprint.mutableMethodOrThrow().apply {
            addInstructionsWithLabels(
                0,
                """
                    invoke-static {}, $SANITIZE_METHOD_DESCRIPTOR
                    move-result v0
                    if-eqz v0, :off
                    return-object p0
                    """, ExternalLabel("off", getInstruction(0))
            )
        }

        updatePatchStatus(
            "enableSanitizeUrlQuery",
            SANITIZE_SHARING_LINKS
        )
    }
}
