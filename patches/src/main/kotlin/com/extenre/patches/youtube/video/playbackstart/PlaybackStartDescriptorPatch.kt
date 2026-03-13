/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.video.playbackstart

import com.extenre.patcher.extensions.InstructionExtensions.getInstruction
import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patches.youtube.utils.extension.sharedExtensionPatch
import com.extenre.util.fingerprint.methodOrThrow
import com.extenre.util.getReference
import com.extenre.util.indexOfFirstInstructionOrThrow
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.Reference

internal lateinit var playbackStartVideoIdReference: Reference

val playbackStartDescriptorPatch = bytecodePatch(
    description = "playbackStartDescriptorPatch"
) {
    dependsOn(sharedExtensionPatch)

    execute {
        // Find the obfuscated method name for PlaybackStartDescriptor.videoId()
        playbackStartFeatureFlagFingerprint.methodOrThrow().apply {
            val stringMethodIndex = indexOfFirstInstructionOrThrow {
                val reference = getReference<MethodReference>()
                reference?.definingClass == PLAYBACK_START_DESCRIPTOR_CLASS_DESCRIPTOR
                        && reference.returnType == "Ljava/lang/String;"
            }

            playbackStartVideoIdReference =
                getInstruction<ReferenceInstruction>(stringMethodIndex).reference
        }
    }
}

