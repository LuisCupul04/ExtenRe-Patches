/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.utils.flyoutmenu

import app.morphe.patches.youtube.utils.resourceid.videoQualityUnavailableAnnouncement
import app.morphe.util.fingerprint.legacyFingerprint
import app.morphe.util.or
import com.android.tools.smali.dexlib2.AccessFlags

internal val videoQualityBottomSheetClassFingerprint = legacyFingerprint(
    name = "videoQualityBottomSheetClassFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("Z"),
    literals = listOf(videoQualityUnavailableAnnouncement),
)
