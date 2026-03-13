/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.flyoutmenu

import com.extenre.patches.youtube.utils.resourceid.videoQualityUnavailableAnnouncement
import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags

internal val videoQualityBottomSheetClassFingerprint = legacyFingerprint(
    name = "videoQualityBottomSheetClassFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("Z"),
    literals = listOf(videoQualityUnavailableAnnouncement),
)
