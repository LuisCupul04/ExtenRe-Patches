/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.music.utils.mainactivity

import app.morphe.util.fingerprint.legacyFingerprint

internal val mainActivityFingerprint = legacyFingerprint(
    name = "mainActivityFingerprint",
    returnType = "V",
    parameters = listOf("Landroid/os/Bundle;"),
    strings = listOf(
        "android.intent.action.MAIN",
        "FEmusic_home"
    ),
    customFingerprint = { method, classDef ->
        method.name == "onCreate" && classDef.endsWith("Activity;")
    }
)
