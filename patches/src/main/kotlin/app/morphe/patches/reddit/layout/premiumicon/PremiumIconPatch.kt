/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.reddit.layout.premiumicon

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.reddit.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.morphe.patches.reddit.utils.fix.signature.spoofSignaturePatch
import app.morphe.patches.reddit.utils.patch.PatchList.PREMIUM_ICON
import app.morphe.patches.reddit.utils.settings.updatePatchStatus
import app.morphe.util.fingerprint.mutableMethodOrThrow

@Suppress("unused")
val premiumIconPatch = bytecodePatch(
    name = PREMIUM_ICON.key,
    description = "${PREMIUM_ICON.title}: ${PREMIUM_ICON.summary}",
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(spoofSignaturePatch)

    execute {
        premiumIconFingerprint.mutableMethodOrThrow().addInstructions(
            0, """
                const/4 v0, 0x1
                return v0
                """
        )

        updatePatchStatus(PREMIUM_ICON)
    }
}
