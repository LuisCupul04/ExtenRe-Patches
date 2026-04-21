/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.utils.fix.bottomui

import app.morphe.util.fingerprint.legacyFingerprint

internal const val FULLSCREEN_BUTTON_POSITION_FEATURE_FLAG = 45627640L
internal const val FULLSCREEN_BUTTON_VIEW_STUB_FEATURE_FLAG = 45617294L
internal const val PLAYER_BOTTOM_CONTROLS_EXPLODER_FEATURE_FLAG = 45643739L

internal val fullscreenButtonPositionFingerprint = legacyFingerprint(
    name = "fullscreenButtonPositionFingerprint",
    returnType = "Z",
    literals = listOf(FULLSCREEN_BUTTON_POSITION_FEATURE_FLAG),
)

internal val fullscreenButtonViewStubFingerprint = legacyFingerprint(
    name = "fullscreenButtonViewStubFingerprint",
    returnType = "Z",
    literals = listOf(FULLSCREEN_BUTTON_VIEW_STUB_FEATURE_FLAG),
)

internal val playerBottomControlsExploderFeatureFlagFingerprint = legacyFingerprint(
    name = "playerBottomControlsExploderFeatureFlagFingerprint",
    returnType = "Z",
    literals = listOf(PLAYER_BOTTOM_CONTROLS_EXPLODER_FEATURE_FLAG),
)
