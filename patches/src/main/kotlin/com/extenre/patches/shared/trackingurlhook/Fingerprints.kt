/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.shared.trackingurlhook

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

internal val trackingUrlModelFingerprint = legacyFingerprint(
    name = "trackingUrlModelFingerprint",
    returnType = "Landroid/net/Uri;",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = emptyList(),
    opcodes = listOf(
        Opcode.IGET_OBJECT,
        Opcode.INVOKE_STATIC,
        Opcode.MOVE_RESULT_OBJECT,
    ),
)

/**
 * On YouTube, this class is 'Lcom/google/android/libraries/youtube/innertube/model/player/TrackingUrlModel;'
 * On YouTube Music, class names are obfuscated.
 */
internal val trackingUrlModelToStringFingerprint = legacyFingerprint(
    name = "trackingUrlModelToStringFingerprint",
    returnType = "Ljava/lang/String;",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = emptyList(),
    strings = listOf(
        "@",
        "baseUrl->",
        "params->",
        "headers->",
    )
)
