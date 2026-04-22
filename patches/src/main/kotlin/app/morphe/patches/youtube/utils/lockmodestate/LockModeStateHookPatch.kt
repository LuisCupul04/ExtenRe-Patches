/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.utils.lockmodestate

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.removeInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.youtube.utils.extension.Constants.UTILS_PATH
import app.morphe.util.fingerprint.matchOrThrow
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

private const val EXTENSION_CLASS_DESCRIPTOR =
    "$UTILS_PATH/LockModeStateHookPatch;"

val lockModeStateHookPatch = bytecodePatch(
    description = "lockModeStateHookPatch"
) {

    execute {

        lockModeStateFingerprint.matchOrThrow().let {
            it.method.apply {
                // ✅ Morphe: usar instructionMatches en lugar de patternMatch
                val insertIndex = it.instructionMatches.last().index
                val insertRegister = getInstruction<OneRegisterInstruction>(insertIndex).registerA

                addInstructions(
                    insertIndex + 1, """
                        invoke-static {v$insertRegister}, $EXTENSION_CLASS_DESCRIPTOR->setLockModeState(Ljava/lang/Enum;)V
                        return-object v$insertRegister
                        """
                )
                removeInstruction(insertIndex)
            }
        }

    }
}
