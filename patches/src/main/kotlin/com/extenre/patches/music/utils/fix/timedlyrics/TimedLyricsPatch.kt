/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.utils.fix.timedlyrics

import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patches.music.utils.playservice.is_8_33_or_greater
import com.extenre.patches.music.utils.playservice.versionCheckPatch
import com.extenre.util.fingerprint.injectLiteralInstructionBooleanCall

val timedLyricsPatch = bytecodePatch(
    description = "timedLyricsPatch"
) {
    dependsOn(versionCheckPatch)

    execute {
        if (!is_8_33_or_greater) {
            return@execute
        }

        /**
         * When these experimental flags are enabled, the real-time lyrics UI will break.
         */
        mapOf(
            timedLyricsPrimaryFingerprint to TIMED_LYRICS_PRIMARY_FEATURE_FLAG,
            timedLyricsSecondaryFingerprint to TIMED_LYRICS_SECONDARY_FEATURE_FLAG,
        ).forEach { (fingerprint, literal) ->
            fingerprint.injectLiteralInstructionBooleanCall(
                literal,
                "0x0"
            )
        }
    }
}
