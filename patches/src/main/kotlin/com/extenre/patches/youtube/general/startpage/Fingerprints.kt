/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.general.startpage

import com.extenre.util.fingerprint.legacyFingerprint
import com.android.tools.smali.dexlib2.Opcode

internal val browseIdFingerprint = legacyFingerprint(
    name = "browseIdFingerprint",
    returnType = "Lcom/google/android/apps/youtube/app/common/ui/navigation/PaneDescriptor;",
    parameters = emptyList(),
    opcodes = listOf(
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.RETURN_OBJECT,
    ),
    strings = listOf("FEwhat_to_watch"),
)

internal val intentFingerprint = legacyFingerprint(
    name = "intentFingerprint",
    parameters = listOf("Landroid/content/Intent;"),
    strings = listOf("has_handled_intent"),
)
