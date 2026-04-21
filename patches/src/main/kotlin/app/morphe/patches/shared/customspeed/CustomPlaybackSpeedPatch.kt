/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.shared.customspeed

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.fingerprint.matchOrThrow
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.getReference
import app.morphe.util.indexOfFirstInstructionOrThrow
import app.morphe.util.indexOfFirstLiteralInstructionOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.util.MethodUtil

private var patchIncluded = false

fun customPlaybackSpeedPatch(
    descriptor: String,
    maxSpeed: Float
) = bytecodePatch(
    name = "custom-Playback-Speed-Patch",
    description = "customPlaybackSpeedPatch"
) {
    execute {
        if (patchIncluded) {
            return@execute
        }

        val arrayGeneratorMatch = arrayGeneratorFingerprint.matchOrThrow()
        val method = arrayGeneratorMatch.method
        val classDef = arrayGeneratorMatch.classDef
        val mutableMethod = proxy(classDef).mutableClass.methods.first {
            MethodUtil.methodSignaturesMatch(it, method)
        }
        mutableMethod.apply {
            val targetIndex = arrayGeneratorMatch.patternMatch!!.startIndex
            val targetRegister = getInstruction<OneRegisterInstruction>(targetIndex).registerA

            addInstructions(
                targetIndex + 1, """
                        invoke-static {v$targetRegister}, $descriptor->getLength(I)I
                        move-result v$targetRegister
                        """
            )

            val sizeIndex = indexOfFirstInstructionOrThrow {
                getReference<MethodReference>()?.name == "size"
            } + 1
            val sizeRegister = getInstruction<OneRegisterInstruction>(sizeIndex).registerA

            addInstructions(
                sizeIndex + 1, """
                        invoke-static {v$sizeRegister}, $descriptor->getSize(I)I
                        move-result v$sizeRegister
                        """
            )

            val arrayIndex = indexOfFirstInstructionOrThrow {
                getReference<FieldReference>()?.type == "[F"
            }
            val arrayRegister = getInstruction<OneRegisterInstruction>(arrayIndex).registerA

            addInstructions(
                arrayIndex + 1, """
                        invoke-static {v$arrayRegister}, $descriptor->getArray([F)[F
                        move-result-object v$arrayRegister
                        """
            )
        }

        setOf(
            limiterFallBackFingerprint.mutableMethodOrThrow(),
            limiterFingerprint.mutableMethodOrThrow(limiterFallBackFingerprint)
        ).forEach { method ->
            method.apply {
                val limitMinIndex =
                    indexOfFirstLiteralInstructionOrThrow(0.25f.toRawBits().toLong())
                val limitMaxIndex =
                    indexOfFirstInstructionOrThrow(limitMinIndex + 1, Opcode.CONST_HIGH16)

                val limitMinRegister =
                    getInstruction<OneRegisterInstruction>(limitMinIndex).registerA
                val limitMaxRegister =
                    getInstruction<OneRegisterInstruction>(limitMaxIndex).registerA

                replaceInstruction(
                    limitMinIndex,
                    "const/high16 v$limitMinRegister, 0x0"
                )
                replaceInstruction(
                    limitMaxIndex,
                    "const/high16 v$limitMaxRegister, ${maxSpeed.toRawBits()}"
                )
            }
        }

        patchIncluded = true

    }
}
