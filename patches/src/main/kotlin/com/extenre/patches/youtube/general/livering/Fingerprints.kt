/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.general.livering

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.getReference
import com.extenre.util.indexOfFirstInstruction
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

internal val clientSettingEndpointFingerprint = legacyFingerprint(
    name = "clientSettingEndpointFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("L", "Ljava/util/Map;"),
    strings = listOf(
        "force_fullscreen",
        "PLAYBACK_START_DESCRIPTOR_MUTATOR",
        "VideoPresenterConstants.VIDEO_THUMBNAIL_BITMAP_KEY"
    ),
    customFingerprint = { method, _ ->
        indexOfPlaybackStartDescriptorInstruction(method) >= 0
    }
)

internal fun indexOfPlaybackStartDescriptorInstruction(method: Method) =
    method.indexOfFirstInstruction {
        val reference = getReference<MethodReference>()
        opcode == Opcode.INVOKE_VIRTUAL &&
                reference?.returnType == "Lcom/google/android/libraries/youtube/player/model/PlaybackStartDescriptor;" &&
                reference.parameterTypes.isEmpty()
    }
