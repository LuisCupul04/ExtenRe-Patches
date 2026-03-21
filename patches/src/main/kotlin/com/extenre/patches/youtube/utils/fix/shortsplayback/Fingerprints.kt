/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.fix.shortsplayback

import com.extenre.util.fingerprint.legacyFingerprint

internal const val SHORTS_PLAYBACK_PRIMARY_FEATURE_FLAG = 45387052L

internal val shortsPlaybackPrimaryFingerprint = legacyFingerprint(
    name = "shortsPlaybackPrimaryFingerprint",
    returnType = "Z",
    literals = listOf(SHORTS_PLAYBACK_PRIMARY_FEATURE_FLAG),
)

internal const val SHORTS_PLAYBACK_SECONDARY_FEATURE_FLAG = 45378771L

internal val shortsPlaybackSecondaryFingerprint = legacyFingerprint(
    name = "shortsPlaybackSecondaryFingerprint",
    returnType = "L",
    literals = listOf(SHORTS_PLAYBACK_SECONDARY_FEATURE_FLAG),
)