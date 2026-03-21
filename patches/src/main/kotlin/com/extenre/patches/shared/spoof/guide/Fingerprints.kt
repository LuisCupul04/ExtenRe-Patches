/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.shared.spoof.guide

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val guideEndpointConstructorFingerprint = legacyFingerprint(
    name = "guideEndpointConstructorFingerprint",
    returnType = "V",
    strings = listOf("guide"),
    customFingerprint = { method, _ ->
        method.name == "<init>"
    }
)

internal val guideEndpointRequestBodyFingerprint = legacyFingerprint(
    name = "guideEndpointRequestBodyFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PROTECTED or AccessFlags.FINAL,
    parameters = emptyList(),
    opcodes = listOf(Opcode.RETURN_VOID),
)

