/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("SpellCheckingInspection")

package com.extenre.patches.shared.quic

import com.extenre.util.fingerprint.legacyFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal val cronetEngineBuilderFingerprint = legacyFingerprint(
    name = "cronetEngineBuilderFingerprint",
    returnType = "L",
    accessFlags = AccessFlags.PUBLIC.value,
    parameters = listOf("Z"),
    customFingerprint = { method, _ ->
        method.definingClass.endsWith("/CronetEngine\$Builder;") &&
                method.name == "enableQuic"
    }
)

internal val experimentalCronetEngineBuilderFingerprint = legacyFingerprint(
    name = "experimentalCronetEngineBuilderFingerprint",
    returnType = "L",
    accessFlags = AccessFlags.PUBLIC.value,
    parameters = listOf("Z"),
    customFingerprint = { method, _ ->
        method.definingClass.endsWith("/ExperimentalCronetEngine\$Builder;") &&
                method.name == "enableQuic"
    }
)