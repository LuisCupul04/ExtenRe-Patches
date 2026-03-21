/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.utils.fix.timedlyrics

import com.extenre.util.fingerprint.legacyFingerprint

internal const val TIMED_LYRICS_PRIMARY_FEATURE_FLAG = 45685201L
internal const val TIMED_LYRICS_SECONDARY_FEATURE_FLAG = 45688384L

internal val timedLyricsPrimaryFingerprint = legacyFingerprint(
    name = "timedLyricsFingerprint",
    returnType = "Z",
    literals = listOf(TIMED_LYRICS_PRIMARY_FEATURE_FLAG),
)

internal val timedLyricsSecondaryFingerprint = legacyFingerprint(
    name = "timedLyricsSecondaryFingerprint",
    returnType = "Z",
    literals = listOf(TIMED_LYRICS_SECONDARY_FEATURE_FLAG),
)
