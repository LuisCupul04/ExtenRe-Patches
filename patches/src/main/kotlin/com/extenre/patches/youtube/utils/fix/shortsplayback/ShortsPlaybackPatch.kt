/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.fix.shortsplayback

import com.extenre.patcher.patch.bytecodePatch
import com.extenre.util.fingerprint.injectLiteralInstructionBooleanCall

val shortsPlaybackPatch = bytecodePatch(
    description = "shortsPlaybackPatch"
) {

    execute {
        /**
         * This issue only affects some versions of YouTube.
         * Therefore, this patch only applies to versions that can resolve this fingerprint.
         *
         * RVX applies default video quality to Shorts as well, so this patch is required.
         */
        mapOf(
            shortsPlaybackPrimaryFingerprint to SHORTS_PLAYBACK_PRIMARY_FEATURE_FLAG,
            shortsPlaybackSecondaryFingerprint to SHORTS_PLAYBACK_SECONDARY_FEATURE_FLAG
        ).forEach { (fingerprint, literal) ->
            fingerprint.injectLiteralInstructionBooleanCall(
                literal,
                "0x0"
            )
        }
    }
}
