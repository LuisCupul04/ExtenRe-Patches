/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.fix.bottomui

import com.extenre.patcher.patch.bytecodePatch
import com.extenre.util.fingerprint.injectLiteralInstructionBooleanCall
import com.extenre.util.fingerprint.resolvable

val cfBottomUIPatch = bytecodePatch(
    description = "cfBottomUIPatch"
) {

    execute {
        /**
         * This issue only affects some versions of YouTube.
         * Therefore, this patch only applies to versions that can resolve this fingerprint.
         */
        mapOf(
            fullscreenButtonPositionFingerprint to FULLSCREEN_BUTTON_POSITION_FEATURE_FLAG,
            fullscreenButtonViewStubFingerprint to FULLSCREEN_BUTTON_VIEW_STUB_FEATURE_FLAG,
            playerBottomControlsExploderFeatureFlagFingerprint to PLAYER_BOTTOM_CONTROLS_EXPLODER_FEATURE_FLAG,
        ).forEach { (fingerprint, literalValue) ->
            if (fingerprint.resolvable()) {
                fingerprint.injectLiteralInstructionBooleanCall(
                    literalValue,
                    "0x0"
                )
            }
        }


    }
}
