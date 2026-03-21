/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.shared.opus

import com.extenre.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import com.extenre.patcher.extensions.InstructionExtensions.getInstruction
import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patcher.util.smali.ExternalLabel
import com.extenre.patches.shared.extension.Constants.PATCHES_PATH
import com.extenre.util.fingerprint.matchOrThrow
import com.extenre.util.fingerprint.methodOrThrow
import com.extenre.util.getReference
import com.extenre.util.indexOfFirstInstructionOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

private const val EXTENSION_CLASS_DESCRIPTOR =
    "$PATCHES_PATH/OpusCodecPatch;"

fun baseOpusCodecsPatch() = bytecodePatch(
    description = "baseOpusCodecsPatch"
) {
    execute {
        val opusCodecReference = with(codecReferenceFingerprint.methodOrThrow()) {
            val codecIndex = indexOfFirstInstructionOrThrow {
                opcode == Opcode.INVOKE_STATIC &&
                        getReference<MethodReference>()?.returnType == "Ljava/util/Set;"
            }
            getInstruction<ReferenceInstruction>(codecIndex).reference
        }

        codecSelectorFingerprint.matchOrThrow().let {
            it.method.apply {
                val freeRegister = implementation!!.registerCount - parameters.size - 2
                val targetIndex = it.patternMatch!!.endIndex
                val targetRegister = getInstruction<OneRegisterInstruction>(targetIndex).registerA

                addInstructionsWithLabels(
                    targetIndex + 1, """
                        invoke-static {}, $EXTENSION_CLASS_DESCRIPTOR->enableOpusCodec()Z
                        move-result v$freeRegister
                        if-eqz v$freeRegister, :mp4a
                        invoke-static {}, $opusCodecReference
                        move-result-object v$targetRegister
                        """, ExternalLabel("mp4a", getInstruction(targetIndex + 1))
                )
            }
        }
    }
}

