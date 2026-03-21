/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.general.music

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.getReference
import com.extenre.util.indexOfFirstInstruction
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

internal val appDeepLinkFingerprint = legacyFingerprint(
    name = "appDeepLinkFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("L", "Ljava/util/Map;"),
    opcodes = listOf(
        Opcode.IGET_OBJECT,
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT,
        Opcode.CONST_STRING,
    ),
    strings = listOf("android.intent.action.VIEW"),
    customFingerprint = { method, _ ->
        method.indexOfFirstInstruction {
            getReference<FieldReference>()?.name == "appDeepLinkEndpoint"
        } >= 0
    }
)
