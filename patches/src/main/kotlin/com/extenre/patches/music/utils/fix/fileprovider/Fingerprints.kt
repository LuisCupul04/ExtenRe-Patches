/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.utils.fix.fileprovider

import com.extenre.util.fingerprint.legacyFingerprint

internal val fileProviderResolverFingerprint = legacyFingerprint(
    name = "fileProviderResolverFingerprint",
    returnType = "L",
    strings = listOf(
        "android.support.FILE_PROVIDER_PATHS",
        "Name must not be empty"
    )
)