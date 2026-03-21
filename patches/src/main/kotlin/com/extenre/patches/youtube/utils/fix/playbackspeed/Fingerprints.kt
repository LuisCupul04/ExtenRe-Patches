/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.fix.playbackspeed

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.getReference
import com.extenre.util.indexOfFirstInstructionReversed
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

/**
 * This fingerprint is compatible with YouTube 17.34.36 ~ 19.50.40.
 *
 * This method is usually used to set the initial speed (1.0x) when playback starts from the feed.
 * For some reason, in the latest YouTube, it is invoked even after the video has already started.
 */
internal val playbackSpeedInFeedsFingerprint = legacyFingerprint(
    name = "playbackSpeedInFeedsFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("L"),
    opcodes = listOf(
        Opcode.IGET,
        Opcode.MUL_INT_LIT16,
        Opcode.IGET_WIDE,
        Opcode.CONST_WIDE_16,
        Opcode.CMP_LONG,
        Opcode.IF_EQZ,
        Opcode.IF_LEZ,
        Opcode.SUB_LONG_2ADDR,
    ),
    customFingerprint = { method, _ ->
        indexOfGetPlaybackSpeedInstruction(method) >= 0
    }
)

fun indexOfGetPlaybackSpeedInstruction(method: Method) =
    method.indexOfFirstInstructionReversed {
        opcode == Opcode.IGET &&
                getReference<FieldReference>()?.type == "F"
    }
