/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.webview

import com.extenre.util.fingerprint.legacyFingerprint

internal val vrWelcomeActivityOnCreateFingerprint = legacyFingerprint(
    name = "vrWelcomeActivityOnCreateFingerprint",
    returnType = "V",
    parameters = listOf("Landroid/os/Bundle;"),
    customFingerprint = { method, classDef ->
        classDef.type == "Lcom/google/android/libraries/youtube/player/features/gl/vr/VrWelcomeActivity;"
                && method.name == "onCreate"
    }
)
