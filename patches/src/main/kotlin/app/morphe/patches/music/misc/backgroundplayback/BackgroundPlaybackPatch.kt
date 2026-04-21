/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.music.misc.backgroundplayback

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.music.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.morphe.patches.music.utils.patch.PatchList.REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS
import app.morphe.patches.music.utils.settings.ResourceUtils.updatePatchStatus
import app.morphe.patches.music.utils.settings.settingsPatch
import app.morphe.util.fingerprint.matchOrThrow
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.fingerprint.resolvable
import app.morphe.util.getReference
import app.morphe.util.getWalkerMethod
import app.morphe.util.indexOfFirstInstructionOrThrow
import app.morphe.util.indexOfFirstStringInstructionOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.util.MethodUtil

@Suppress("unused")
val backgroundPlaybackPatch = bytecodePatch(
    name = REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS.key,
    description = "${REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS.title}: ${REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS.summary}",
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(settingsPatch)

    execute {
        // region patch for background play

        backgroundPlaybackManagerFingerprint.mutableMethodOrThrow().addInstructions(
            0, """
                const/4 v0, 0x1
                return v0
                """
        )

        // endregion

        // region patch for exclusive audio playback

        // don't play music video
        musicBrowserServiceFingerprint.matchOrThrow().let {
            val musicMethod = it.method
            val musicClassDef = it.classDef
            val musicMutableMethod = proxy(musicClassDef).mutableClass.methods.first {
                MethodUtil.methodSignaturesMatch(it, musicMethod)
            }
            musicMutableMethod.apply {
                val stringIndex = it.stringMatches!!.first().index
                val targetIndex = indexOfFirstInstructionOrThrow(stringIndex) {
                    val reference = getReference<MethodReference>()
                    opcode == Opcode.INVOKE_VIRTUAL &&
                            reference?.returnType == "Z" &&
                            reference.parameterTypes.size == 0
                }

                getWalkerMethod(targetIndex).addInstructions(
                    0, """
                        const/4 v0, 0x1
                        return v0
                        """
                )
            }
        }

        // don't play podcast videos
        // enable by default from YouTube Music 7.05.52+

        if (podCastConfigFingerprint.resolvable() &&
            dataSavingSettingsFragmentFingerprint.resolvable()
        ) {
            podCastConfigFingerprint.mutableMethodOrThrow().apply {
                val insertIndex = implementation!!.instructions.size - 1
                val targetRegister = getInstruction<OneRegisterInstruction>(insertIndex).registerA

                addInstruction(
                    insertIndex,
                    "const/4 v$targetRegister, 0x1"
                )
            }

            dataSavingSettingsFragmentFingerprint.mutableMethodOrThrow().apply {
                val insertIndex =
                    indexOfFirstStringInstructionOrThrow("pref_key_dont_play_nma_video") + 4
                val targetRegister = getInstruction<FiveRegisterInstruction>(insertIndex).registerD

                addInstruction(
                    insertIndex,
                    "const/4 v$targetRegister, 0x1"
                )
            }
        }

        // endregion

        // region patch for minimized playback

        kidsBackgroundPlaybackPolicyControllerFingerprint.mutableMethodOrThrow().addInstruction(
            0, "return-void"
        )

        // endregion

        updatePatchStatus(REMOVE_BACKGROUND_PLAYBACK_RESTRICTIONS)

    }
}
