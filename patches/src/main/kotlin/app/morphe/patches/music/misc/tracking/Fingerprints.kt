/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.music.misc.tracking

import app.morphe.util.fingerprint.legacyFingerprint

/**
 * Sharing panel of Lyrics
 */
internal val imageShareLinkFormatterFingerprint = legacyFingerprint(
    name = "imageShareLinkFormatterFingerprint",
    returnType = "V",
    strings = listOf(
        "android.intent.extra.TEXT",
        "Image Uri is null.",
    )
)