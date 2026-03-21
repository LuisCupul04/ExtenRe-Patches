/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.bottomsheet

import com.extenre.patcher.extensions.InstructionExtensions.addInstruction
import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patches.youtube.utils.extension.Constants.UTILS_PATH
import com.extenre.util.findMethodOrThrow
import com.extenre.util.fingerprint.definingClassOrThrow

private const val EXTENSION_BOTTOM_SHEET_HOOK_CLASS_DESCRIPTOR =
    "$UTILS_PATH/BottomSheetHookPatch;"

val bottomSheetHookPatch = bytecodePatch(
    description = "bottomSheetHookPatch"
) {
    execute {
        val bottomSheetClass =
            bottomSheetBehaviorFingerprint.definingClassOrThrow()

        arrayOf(
            "onAttachedToWindow",
            "onDetachedFromWindow"
        ).forEach { methodName ->
            findMethodOrThrow(bottomSheetClass) {
                name == methodName
            }.addInstruction(
                1,
                "invoke-static {}, $EXTENSION_BOTTOM_SHEET_HOOK_CLASS_DESCRIPTOR->$methodName()V"
            )
        }
    }
}
