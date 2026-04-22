/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.utils.fullscreen

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.mutableTypes.MutableMethod
import app.morphe.patches.youtube.utils.extension.Constants.EXTENSION_PATH
import app.morphe.patches.youtube.utils.extension.sharedExtensionPatch
import app.morphe.patches.youtube.utils.playservice.is_20_02_or_greater
import app.morphe.patches.youtube.utils.playservice.versionCheckPatch
import app.morphe.util.addStaticFieldToExtension
import app.morphe.util.findMethodOrThrow
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.getReference
import app.morphe.util.getWalkerMethod
import app.morphe.util.indexOfFirstInstructionOrThrow
import app.morphe.util.indexOfFirstInstructionReversedOrThrow
import app.morphe.util.mutableClassDefBy
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.TypeReference
import com.android.tools.smali.dexlib2.util.MethodUtil

private const val EXTENSION_VIDEO_UTILS_CLASS_DESCRIPTOR =
    "$EXTENSION_PATH/utils/VideoUtils;"

internal var enterFullscreenMethods = mutableListOf<MutableMethod>()

val fullscreenButtonHookPatch = bytecodePatch(
    name = "fullscreen-Button-Hook-Patch",
    description = "fullscreenButtonHookPatch"
) {

    dependsOn(
        sharedExtensionPatch,
        versionCheckPatch,
    )

    execute {
        fun getParameters(): Pair<MutableMethod, String> {
            nextGenWatchLayoutFullscreenModeFingerprint.mutableMethodOrThrow().apply {
                val methodIndex = indexOfFirstInstructionReversedOrThrow {
                    opcode == Opcode.INVOKE_DIRECT &&
                            getReference<MethodReference>()?.parameterTypes?.size == 2
                }
                val fieldIndex =
                    indexOfFirstInstructionReversedOrThrow(methodIndex, Opcode.IGET_OBJECT)
                val fullscreenActionClass =
                    (getInstruction<ReferenceInstruction>(fieldIndex).reference as FieldReference).type

                if (is_20_02_or_greater) {
                    val setAnimatorListenerIndex =
                        indexOfFirstInstructionOrThrow(methodIndex, Opcode.INVOKE_VIRTUAL)
                    getWalkerMethod(setAnimatorListenerIndex).apply {
                        val addListenerIndex = indexOfFirstInstructionOrThrow {
                            opcode == Opcode.INVOKE_VIRTUAL &&
                                    getReference<MethodReference>()?.name == "addListener"
                        }
                        val animatorListenerAdapterClass = getInstruction<ReferenceInstruction>(
                            indexOfFirstInstructionReversedOrThrow(
                                addListenerIndex,
                                Opcode.NEW_INSTANCE
                            )
                        ).reference.toString()
                        // ✅ Morphe: obtener método y clase mutable
                        val method = findMethodOrThrow(animatorListenerAdapterClass) { parameters.isEmpty() }
                        // ✅ Morphe: usar classDefs en lugar de classes
                        val classDef = classDefs.find { it.type == animatorListenerAdapterClass }
                            ?: throw PatchException("Class not found: $animatorListenerAdapterClass")
                        // ✅ Morphe: usar mutableClassDefBy en lugar de proxy
                        val mutableMethod = mutableClassDefBy(classDef).methods.first {
                            MethodUtil.methodSignaturesMatch(it, method)
                        }
                        return Pair(mutableMethod, fullscreenActionClass)
                    }
                } else {
                    val animatorListenerClass =
                        (getInstruction<ReferenceInstruction>(methodIndex).reference as MethodReference).definingClass
                    // ✅ Morphe: obtener método y clase mutable
                    val method = findMethodOrThrow(animatorListenerClass) { parameters == listOf("I") }
                    // ✅ Morphe: usar classDefs en lugar de classes
                    val classDef = classDefs.find { it.type == animatorListenerClass }
                        ?: throw PatchException("Class not found: $animatorListenerClass")
                    // ✅ Morphe: usar mutableClassDefBy en lugar de proxy
                    val mutableMethod = mutableClassDefBy(classDef).methods.first {
                        MethodUtil.methodSignaturesMatch(it, method)
                    }
                    return Pair(mutableMethod, fullscreenActionClass)
                }
            }
        }

        val (animatorListenerMethod, fullscreenActionClass) = getParameters()

        val (enterFullscreenReference, exitFullscreenReference, opcodeName) =
            with(animatorListenerMethod) {
                val enterFullscreenIndex = indexOfFirstInstructionOrThrow {
                    val reference = getReference<MethodReference>()
                    reference?.returnType == "V" &&
                            reference.definingClass == fullscreenActionClass &&
                            reference.parameterTypes.isEmpty()
                }
                val exitFullscreenIndex = indexOfFirstInstructionReversedOrThrow {
                    val reference = getReference<MethodReference>()
                    reference?.returnType == "V" &&
                            reference.definingClass == fullscreenActionClass &&
                            reference.parameterTypes.isEmpty()
                }

                val enterFullscreenReference =
                    getInstruction<ReferenceInstruction>(enterFullscreenIndex).reference
                val exitFullscreenReference =
                    getInstruction<ReferenceInstruction>(exitFullscreenIndex).reference
                val opcode = getInstruction(enterFullscreenIndex).opcode

                val enterFullscreenClass =
                    (enterFullscreenReference as MethodReference).definingClass

                if (opcode == Opcode.INVOKE_INTERFACE) {
                    // ✅ Morphe: usar classDefs en lugar de classes
                    classDefs.forEach { classDef ->
                        if (enterFullscreenMethods.size >= 2)
                            return@forEach
                        if (!classDef.interfaces.contains(enterFullscreenClass))
                            return@forEach

                        // ✅ Morphe: obtener clase mutable directamente
                        val enterFullscreenMethod =
                            mutableClassDefBy(classDef)
                                .methods
                                .find { method -> method.name == enterFullscreenReference.name }
                                ?: throw PatchException("No matching classes: $enterFullscreenClass")

                        enterFullscreenMethods.add(enterFullscreenMethod)
                    }
                } else {
                    // ✅ Morphe: obtener método y clase mutable
                    val method = findMethodOrThrow(enterFullscreenClass) {
                        name == enterFullscreenReference.name
                    }
                    // ✅ Morphe: usar classDefs en lugar de classes
                    val classDef = classDefs.find { it.type == enterFullscreenClass }
                        ?: throw PatchException("Class not found: $enterFullscreenClass")
                    // ✅ Morphe: usar mutableClassDefBy en lugar de proxy
                    val mutableMethod = mutableClassDefBy(classDef).methods.first {
                        MethodUtil.methodSignaturesMatch(it, method)
                    }
                    enterFullscreenMethods.add(mutableMethod)
                }

                Triple(
                    enterFullscreenReference,
                    exitFullscreenReference,
                    opcode.name
                )
            }

        nextGenWatchLayoutConstructorFingerprint.mutableMethodOrThrow().apply {
            val targetIndex = indexOfFirstInstructionReversedOrThrow {
                opcode == Opcode.CHECK_CAST &&
                        getReference<TypeReference>()?.type == fullscreenActionClass
            }
            val targetRegister = getInstruction<OneRegisterInstruction>(targetIndex).registerA

            addInstruction(
                targetIndex + 1,
                "sput-object v$targetRegister, $EXTENSION_VIDEO_UTILS_CLASS_DESCRIPTOR->fullscreenActionClass:$fullscreenActionClass"
            )

            val enterFullscreenModeSmaliInstructions =
                """
                    if-eqz v0, :ignore
                    $opcodeName {v0}, $enterFullscreenReference
                    :ignore
                    return-void
                    """

            val exitFullscreenModeSmaliInstructions =
                """
                    if-eqz v0, :ignore
                    $opcodeName {v0}, $exitFullscreenReference
                    :ignore
                    return-void
                    """

            addStaticFieldToExtension(
                EXTENSION_VIDEO_UTILS_CLASS_DESCRIPTOR,
                "enterFullscreenMode",
                "fullscreenActionClass",
                fullscreenActionClass,
                enterFullscreenModeSmaliInstructions,
                false
            )

            addStaticFieldToExtension(
                EXTENSION_VIDEO_UTILS_CLASS_DESCRIPTOR,
                "exitFullscreenMode",
                "fullscreenActionClass",
                fullscreenActionClass,
                exitFullscreenModeSmaliInstructions,
                false
            )
        }
    }
}
