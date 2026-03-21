/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.player.comments

import com.extenre.patches.youtube.utils.resourceid.emojiPickerIcon
import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags

internal val shortsLiveStreamEmojiPickerOnClickListenerFingerprint = legacyFingerprint(
    name = "shortsLiveStreamEmojiPickerOnClickListenerFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC.value,
    parameters = listOf("L"),
    literals = listOf(126326492L),
)

internal val shortsLiveStreamEmojiPickerOpacityFingerprint = legacyFingerprint(
    name = "shortsLiveStreamEmojiPickerOpacityFingerprint",
    returnType = "Landroid/widget/ImageView;",
    accessFlags = AccessFlags.PROTECTED or AccessFlags.FINAL,
    parameters = emptyList(),
    literals = listOf(emojiPickerIcon),
)