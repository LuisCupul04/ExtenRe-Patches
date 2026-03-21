/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.lottie

import com.extenre.patcher.extensions.InstructionExtensions.addInstruction
import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patches.youtube.utils.extension.Constants.UTILS_PATH
import com.extenre.util.findMethodOrThrow
import com.extenre.util.fingerprint.methodOrThrow

private const val EXTENSION_CLASS_DESCRIPTOR =
    "$UTILS_PATH/LottieAnimationViewPatch;"

val lottieAnimationViewHookPatch = bytecodePatch(
    description = "lottieAnimationViewHookPatch",
) {
    execute {

        findMethodOrThrow(EXTENSION_CLASS_DESCRIPTOR) {
            name == "setAnimation"
        }.addInstruction(
            0,
            "invoke-virtual {p0, p1}, " +
                    LOTTIE_ANIMATION_VIEW_CLASS_DESCRIPTOR +
                    "->" +
                    setAnimationFingerprint.methodOrThrow().name +
                    "(I)V"
        )

    }
}
