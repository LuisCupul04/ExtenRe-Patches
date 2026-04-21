/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.all.misc.installer

import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.all.misc.transformation.IMethodCall
import app.morphe.patches.all.misc.transformation.filterMapInstruction35c
import app.morphe.patches.all.misc.transformation.transformInstructionsPatch
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

private const val GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending"

@Suppress("unused")
val changeInstallerPackageNamePatch = bytecodePatch(
    name = "Change installer package name",
    description = "Spoof the installer package name to make it appear that the app was installed from the Google Play Store.",
    defaultEnable = false,   // ← Cambiado de 'use = false'
) {
    dependsOn(
        // Remove the restriction of taking screenshots.
        transformInstructionsPatch(
            filterMap = { classDef, _, instruction, instructionIndex ->
                filterMapInstruction35c<MethodCall>(
                    "Lapp/morphe/extension",
                    classDef,
                    instruction,
                    instructionIndex,
                )
            },
            transform = transform@{ mutableMethod, entry ->
                val (_, _, instructionIndex) = entry

                mutableMethod.apply {
                    val targetRegister = (
                            getInstruction(instructionIndex + 1)
                                    as? OneRegisterInstruction ?: return@transform
                            ).registerA

                    replaceInstruction(
                        instructionIndex + 1,
                        "const-string v$targetRegister, \"$GOOGLE_PLAY_STORE_PACKAGE\"",
                    )
                    replaceInstruction(
                        instructionIndex,
                        "const-string v$targetRegister, \"$GOOGLE_PLAY_STORE_PACKAGE\"",
                    )
                }
            },
        ),
    )
}

// El resto del código (enum MethodCall) permanece igual...
