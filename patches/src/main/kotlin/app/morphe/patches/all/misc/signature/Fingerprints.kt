/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.all.misc.signature

import app.morphe.util.fingerprint.legacyFingerprint

internal const val PACKAGE_NAME = "PACKAGE_NAME"
internal const val CERTIFICATE_BASE64 = "CERTIFICATE_BASE64"

internal val applicationFingerprint = legacyFingerprint(
    name = "applicationFingerprint",
    returnType = "V",
    customFingerprint = { _, classDef ->
        classDef.superclass == "Landroid/app/Application;"
    }
)

internal val spoofSignatureFingerprint = legacyFingerprint(
    name = "spoofSignatureFingerprint",
    returnType = "V",
    strings = listOf(
        PACKAGE_NAME,
        CERTIFICATE_BASE64
    ),
    customFingerprint = { method, _ ->
        method.definingClass.endsWith("/SpoofSignaturePatch;") &&
                method.name == "<clinit>"
    }
)
