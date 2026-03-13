/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.utils.returnyoutubedislike

import com.extenre.patches.music.utils.resourceid.buttonIconPaddingMedium
import com.extenre.util.fingerprint.legacyFingerprint
import com.android.tools.smali.dexlib2.Opcode

internal val textComponentFingerprint = legacyFingerprint(
    name = "textComponentFingerprint",
    returnType = "V",
    opcodes = listOf(Opcode.CONST_HIGH16),
    literals = listOf(buttonIconPaddingMedium),
)
