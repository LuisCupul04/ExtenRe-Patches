/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.mainactivity

import com.extenre.util.fingerprint.legacyFingerprint

/**
 * 'WatchWhileActivity' has been renamed to 'MainActivity' in YouTube v18.48.xx+
 * This fingerprint was added to prepare for YouTube v18.48.xx+
 */
internal val mainActivityFingerprint = legacyFingerprint(
    name = "mainActivityFingerprint",
    returnType = "V",
    parameters = listOf("Landroid/os/Bundle;"),
    strings = listOf("PostCreateCalledKey"),
    customFingerprint = { method, _ ->
        method.definingClass.endsWith("Activity;")
                && method.name == "onCreate"
    }
)
