/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.reddit.misc.openlink

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.indexOfFirstInstruction
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction

internal val customReportsFingerprint = legacyFingerprint(
    name = "customReportsFingerprint",
    returnType = "V",
    strings = listOf("https://www.crisistextline.org/", "screenNavigator"),
    customFingerprint = { method, _ ->
        indexOfScreenNavigatorInstruction(method) >= 0
    }
)

fun indexOfScreenNavigatorInstruction(method: Method) =
    method.indexOfFirstInstruction {
        (this as? ReferenceInstruction)?.reference?.toString()
            ?.contains("Landroid/app/Activity;Landroid/net/Uri;") == true
    }

internal val screenNavigatorFingerprint = legacyFingerprint(
    name = "screenNavigatorFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    opcodes = listOf(
        Opcode.CONST_STRING,
        Opcode.INVOKE_STATIC,
        Opcode.CONST_STRING,
        Opcode.INVOKE_STATIC
    ),
    strings = listOf("activity", "uri"),
    customFingerprint = { _, classDef -> classDef.sourceFile == "RedditScreenNavigator.kt" }
)
