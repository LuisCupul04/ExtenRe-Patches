/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.player.descriptions

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.getReference
import com.extenre.util.indexOfFirstInstruction
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

/**
 * This fingerprint is compatible with YouTube v18.35.xx~
 * Nonetheless, the patch works in YouTube v19.05.xx~
 */
internal val textViewComponentFingerprint = legacyFingerprint(
    name = "textViewComponentFingerprint",
    returnType = "V",
    opcodes = listOf(Opcode.CMPL_FLOAT),
    customFingerprint = { method, _ ->
        method.implementation != null &&
                indexOfTextIsSelectableInstruction(method) >= 0
    },
)

internal fun indexOfTextIsSelectableInstruction(method: Method) =
    method.indexOfFirstInstruction {
        val reference = getReference<MethodReference>()
        opcode == Opcode.INVOKE_VIRTUAL &&
                reference?.name == "setTextIsSelectable" &&
                reference.definingClass != "Landroid/widget/TextView;"
    }