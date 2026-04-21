/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.utils.bottomsheet

import app.morphe.patches.youtube.utils.resourceid.designBottomSheet
import app.morphe.util.fingerprint.legacyFingerprint

internal val bottomSheetBehaviorFingerprint = legacyFingerprint(
    name = "bottomSheetBehaviorFingerprint",
    returnType = "V",
    literals = listOf(designBottomSheet),
)
