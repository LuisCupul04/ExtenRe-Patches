/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.shared.drc

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

/**
 * YouTube 19.30 ~
 * YouTube Music 7.13 ~
 */
internal val compressionRatioFingerprint = legacyFingerprint(
    name = "compressionRatioFingerprint",
    returnType = "Lj${'$'}/util/Optional;",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = emptyList(),
    opcodes = listOf(
        Opcode.IF_EQZ,
        Opcode.IGET,
        Opcode.NEG_FLOAT,
    )
)

/**
 * ~ YouTube 19.29
 * ~ YouTube Music 7.12
 */
internal val compressionRatioLegacyFingerprint = legacyFingerprint(
    name = "compressionRatioLegacyFingerprint",
    returnType = "F",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = emptyList(),
    opcodes = listOf(
        Opcode.IGET_OBJECT,
        Opcode.IGET,
        Opcode.RETURN,
    )
)

internal const val VOLUME_NORMALIZATION_EXPERIMENTAL_FEATURE_FLAG = 45425391L

internal val volumeNormalizationConfigFingerprint = legacyFingerprint(
    name = "volumeNormalizationConfigFingerprint",
    literals = listOf(VOLUME_NORMALIZATION_EXPERIMENTAL_FEATURE_FLAG)
)
