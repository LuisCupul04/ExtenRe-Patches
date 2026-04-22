/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.misc.backgroundplayback

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.youtube.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.morphe.patches.youtube.utils.extension.Constants.MISC_PATH
import app.morphe.patches.youtube.utils.patch.PatchList.REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS
import app.morphe.patches.youtube.utils.playertype.playerTypeHookPatch
import app.morphe.patches.youtube.utils.playservice.is_19_34_or_greater
import app.morphe.patches.youtube.utils.playservice.versionCheckPatch
import app.morphe.patches.youtube.utils.settings.ResourceUtils.addPreference
import app.morphe.patches.youtube.utils.settings.settingsPatch
import app.morphe.util.addInstructionsAtControlFlowLabel
import app.morphe.util.findInstructionIndicesReversedOrThrow
import app.morphe.util.fingerprint.injectLiteralInstructionBooleanCall
import app.morphe.util.fingerprint.matchOrThrow
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.fingerprint.originalMethodOrThrow
import app.morphe.util.getReference
import app.morphe.util.mutableClassDefBy
import app.morphe.util.returnEarly
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.util.MethodUtil

private const val EXTENSION_CLASS_DESCRIPTOR =
    "$MISC_PATH/BackgroundPlaybackPatch;"

@Suppress("unused")
val backgroundPlaybackPatch = bytecodePatch(
    name = REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS.key,
    description = "${REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS.title}: ${REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS.summary}",
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(
        playerTypeHookPatch,
        settingsPatch,
        versionCheckPatch,
    )

    execute {
        // region background playback for normal videos and shorts

        arrayOf(
            backgroundPlaybackManagerFingerprint to "isBackgroundPlaybackAllowed",
            backgroundPlaybackManagerShortsFingerprint to "isBackgroundShortsPlaybackAllowed",
        ).forEach { (fingerprint, extensionsMethod) ->
            fingerprint.mutableMethodOrThrow().apply {
                findInstructionIndicesReversedOrThrow(Opcode.RETURN).forEach { index ->
                    val register = getInstruction<OneRegisterInstruction>(index).registerA

                    addInstructionsAtControlFlowLabel(
                        index,
                        """
                            invoke-static { v$register }, $EXTENSION_CLASS_DESCRIPTOR->$extensionsMethod(Z)Z
                            move-result v$register 
                        """,
                    )
                }
            }
        }

        // Enable background playback option in YouTube settings
        backgroundPlaybackSettingsFingerprint.matchOrThrow().let { match ->
            val method = match.method
            val classDef = match.classDef
            // ✅ Morphe: usar mutableClassDefBy con classDef directamente
            val mutableMethod = mutableClassDefBy(classDef).methods.first {
                MethodUtil.methodSignaturesMatch(it, method)
            }
            val booleanCalls = mutableMethod.instructions.withIndex().filter {
                it.value.getReference<MethodReference>()?.returnType == "Z"
            }
            val settingsBooleanIndex = booleanCalls.elementAt(1).index
            // Obtener la instrucción en ese índice
            val invokeInstruction = mutableMethod.getInstruction<ReferenceInstruction>(settingsBooleanIndex)
            val methodRef = invokeInstruction.reference as? MethodReference
                ?: throw PatchException("No method reference at index $settingsBooleanIndex")
            val settingsBooleanMethod = mutableClassDefBy(methodRef.definingClass).methods.first {
                it.name == methodRef.name && it.parameterTypes == methodRef.parameterTypes
            }
            settingsBooleanMethod.returnEarly(true)
        }

        // Force allowing background play for Shorts.
        shortsBackgroundPlaybackFeatureFlagFingerprint.injectLiteralInstructionBooleanCall(
            SHORTS_BACKGROUND_PLAYBACK_FEATURE_FLAG,
            "$EXTENSION_CLASS_DESCRIPTOR->isBackgroundShortsPlaybackAllowed(Z)Z"
        )

        // Fix PiP mode issue.
        if (is_19_34_or_greater) {
            arrayOf(
                backgroundPlaybackManagerCairoFragmentPrimaryFingerprint,
                backgroundPlaybackManagerCairoFragmentSecondaryFingerprint
            ).forEach { fingerprint ->
                fingerprint.matchOrThrow(backgroundPlaybackManagerCairoFragmentParentFingerprint)
                    .let { match ->
                        val method = match.method
                        val classDef = match.classDef
                        // ✅ Morphe: usar mutableClassDefBy con classDef
                        val mutableMethod = mutableClassDefBy(classDef).methods.first {
                            MethodUtil.methodSignaturesMatch(it, method)
                        }
                        mutableMethod.apply {
                            // ✅ Morphe: usar instructionMatches en lugar de patternMatch
                            val insertIndex = match.instructionMatches.first().index + 4
                            val insertRegister =
                                getInstruction<OneRegisterInstruction>(insertIndex).registerA

                            addInstruction(
                                insertIndex,
                                "const/4 v$insertRegister, 0x0"
                            )
                        }
                    }
            }

            pipInputConsumerFeatureFlagFingerprint.injectLiteralInstructionBooleanCall(
                PIP_INPUT_CONSUMER_FEATURE_FLAG,
                "0x0"
            )
        }

        // Force allowing background play for videos labeled for kids.
        kidsBackgroundPlaybackPolicyControllerFingerprint.mutableMethodOrThrow(
            kidsBackgroundPlaybackPolicyControllerParentFingerprint
        ).returnEarly()

        // region add settings
        addPreference(
            arrayOf(
                "PREFERENCE_SCREEN: SHORTS",
                "SETTINGS: DISABLE_SHORTS_BACKGROUND_PLAYBACK"
            ),
            REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS
        )
        // endregion
    }
}
