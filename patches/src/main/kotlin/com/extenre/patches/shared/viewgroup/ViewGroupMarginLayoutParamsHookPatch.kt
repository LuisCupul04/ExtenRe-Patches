/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.shared.viewgroup

import com.extenre.patcher.extensions.InstructionExtensions.addInstructions
import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patches.shared.extension.Constants.EXTENSION_UTILS_CLASS_DESCRIPTOR
import com.extenre.util.findMethodOrThrow
import com.extenre.util.fingerprint.methodOrThrow

val viewGroupMarginLayoutParamsHookPatch = bytecodePatch(
    description = "viewGroupMarginLayoutParamsHookPatch"
) {
    execute {
        val setViewGroupMarginCall = with(
            viewGroupMarginFingerprint.methodOrThrow(viewGroupMarginParentFingerprint)
        ) {
            "$definingClass->$name(Landroid/view/View;II)V"
        }

        findMethodOrThrow(EXTENSION_UTILS_CLASS_DESCRIPTOR) {
            name == "hideViewGroupByMarginLayoutParams"
        }.addInstructions(
            0, """
                const/4 v0, 0x0
                invoke-static {p0, v0, v0}, $setViewGroupMarginCall
                """
        )
    }
}

