/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.music.utils.fix.androidauto

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.music.utils.patch.PatchList.CERTIFICATE_SPOOF
import app.morphe.patches.music.utils.settings.ResourceUtils.updatePatchStatus
import app.morphe.util.fingerprint.mutableMethodOrThrow

@Suppress("unused")
val androidAutoCertificatePatch = bytecodePatch(
    name = CERTIFICATE_SPOOF.key,
    description = "${CERTIFICATE_SPOOF.title}: ${CERTIFICATE_SPOOF.summary}",
) {
    execute {
        certificateCheckFingerprint.mutableMethodOrThrow().addInstructions(
            0, """
                const/4 v0, 0x1
                return v0
                """
        )

        updatePatchStatus(CERTIFICATE_SPOOF)
    }
}
