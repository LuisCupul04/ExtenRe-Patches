/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.sponsorblock

import com.extenre.patches.youtube.utils.extension.Constants.EXTENSION_PATH
import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.getReference
import com.extenre.util.indexOfFirstInstructionReversed
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

internal val rectangleFieldInvalidatorFingerprint = legacyFingerprint(
    name = "rectangleFieldInvalidatorFingerprint",
    returnType = "V",
    parameters = emptyList(),
    customFingerprint = { method, _ ->
        indexOfInvalidateInstruction(method) >= 0
    }
)

internal val segmentPlaybackControllerFingerprint = legacyFingerprint(
    name = "segmentPlaybackControllerFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.STATIC,
    parameters = listOf("Ljava/lang/Object;"),
    opcodes = listOf(Opcode.CONST_STRING),
    customFingerprint = { method, _ ->
        method.definingClass == "$EXTENSION_PATH/sponsorblock/SegmentPlaybackController;"
                && method.name == "setSponsorBarRect"
    }
)

internal fun indexOfInvalidateInstruction(method: Method) =
    method.indexOfFirstInstructionReversed {
        getReference<MethodReference>()?.name == "invalidate"
    }
