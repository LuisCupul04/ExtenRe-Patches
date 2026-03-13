/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.lockmodestate

import com.extenre.util.fingerprint.legacyFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val lockModeStateFingerprint = legacyFingerprint(
    name = "lockModeStateFingerprint",
    returnType = "L",
    accessFlags = AccessFlags.PUBLIC.value,
    parameters = emptyList(),
    opcodes = listOf(Opcode.RETURN_OBJECT),
    customFingerprint = { method, _ ->
        method.name == "getLockModeStateEnum"
    }
)
