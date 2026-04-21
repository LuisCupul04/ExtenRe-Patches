/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.general.loadingscreen

import app.morphe.util.fingerprint.legacyFingerprint

internal const val GRADIENT_LOADING_SCREEN_AB_CONSTANT = 45412406L

internal val useGradientLoadingScreenFingerprint = legacyFingerprint(
    name = "gradientLoadingScreenPrimaryFingerprint",
    literals = listOf(GRADIENT_LOADING_SCREEN_AB_CONSTANT),
)
