/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.shared.drawable

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.getReference
import app.morphe.util.indexOfFirstInstructionReversedOrThrow
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

private lateinit var insertMethod: MutableMethod
private var insertIndex: Int = 0
private var insertRegister: Int = 0
private var offset = 0

val drawableColorHookPatch = bytecodePatch(
    name = "drawable-Color-Hook-Patch",
    description = "drawableColorHookPatch"
) {
    execute {
        drawableColorFingerprint.mutableMethodOrThrow().apply {
            insertMethod = this
            insertIndex = indexOfFirstInstructionReversedOrThrow {
                getReference<MethodReference>()?.name == "setColor"
            }
            insertRegister = getInstruction<FiveRegisterInstruction>(insertIndex).registerD
        }
    }
}

internal fun addDrawableColorHook(
    methodDescriptor: String,
    highPriority: Boolean = false
) {
    insertMethod.addInstructions(
        if (highPriority) insertIndex else insertIndex + offset,
        """
            invoke-static {v$insertRegister}, $methodDescriptor
            move-result v$insertRegister
            """
    )
    offset += 2
}

