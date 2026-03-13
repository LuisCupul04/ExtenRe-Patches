/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.utils.fix.androidauto

import com.extenre.util.fingerprint.legacyFingerprint

internal val certificateCheckFingerprint = legacyFingerprint(
    name = "certificateCheckFingerprint",
    returnType = "Z",
    parameters = listOf("L"),
    strings = listOf("X509")
)