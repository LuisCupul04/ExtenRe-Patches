/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.utils.engagement

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.patches.youtube.utils.extension.Constants.SHARED_PATH
import app.morphe.patches.youtube.utils.extension.sharedExtensionPatch
import app.morphe.patches.youtube.utils.resourceid.sharedResourceIdPatch
import app.morphe.util.findMethodOrThrow
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.getReference
import app.morphe.util.indexOfFirstInstruction
import app.morphe.util.indexOfFirstInstructionOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.util.MethodUtil

private const val EXTENSION_CLASS_DESCRIPTOR =
    "$SHARED_PATH/EngagementPanel;"

internal lateinit var engagementPanelBuilderMethod: MutableMethod
internal var engagementPanelFreeRegister = 0
internal var engagementPanelIdIndex = 0
internal var engagementPanelIdRegister = 0

val engagementPanelHookPatch = bytecodePatch(
    name = "engagement-Panel-Hook-Patch",
    description = "engagementPanelHookPatch"
) {
    dependsOn(
        sharedExtensionPatch,
        sharedResourceIdPatch,
    )

    execute {
        fun Method.setFreeIndex(startIndex: Int) {
            val startRegister = engagementPanelIdRegister
            var index = startIndex
            var register = startRegister

            while (register == startRegister) {
                index = indexOfFirstInstruction(index + 1, Opcode.IGET_OBJECT)
                register = getInstruction<TwoRegisterInstruction>(index).registerA
            }

            engagementPanelFreeRegister = register
        }

        val engagementPanelInfoClass = engagementPanelLayoutFingerprint
            .mutableMethodOrThrow()
            .parameters[0]
            .toString()

        // Reemplazar findmutableMethodOrThrow por búsqueda manual para obtener el método del constructor de la clase de información
        val (engagementPanelIdReference, engagementPanelObjectReference) = run {
            val method = findMethodOrThrow(engagementPanelInfoClass)
            val classDef = classes.find { it.type == engagementPanelInfoClass }
                ?: throw PatchException("Class not found: $engagementPanelInfoClass")
            val mutableMethod = proxy(classDef).mutableClass.methods.first {
                MethodUtil.methodSignaturesMatch(it, method)
            }
            val engagementPanelIdIndex = mutableMethod.indexOfFirstInstructionOrThrow {
                opcode == Opcode.IPUT_OBJECT &&
                        getReference<FieldReference>()?.type == "Ljava/lang/String;"
            }
            val engagementPanelObjectIndex = mutableMethod.indexOfFirstInstructionOrThrow {
                opcode == Opcode.IPUT_OBJECT &&
                        getReference<FieldReference>()?.type != "Ljava/lang/String;"
            }
            Pair(
                mutableMethod.getInstruction<ReferenceInstruction>(engagementPanelIdIndex).reference.toString(),
                mutableMethod.getInstruction<ReferenceInstruction>(engagementPanelObjectIndex).reference.toString(),
            )
        }

        engagementPanelBuilderFingerprint.mutableMethodOrThrow().apply {
            val insertIndex = indexOfFirstInstructionOrThrow {
                opcode == Opcode.IGET_OBJECT &&
                        getReference<FieldReference>()?.toString() == engagementPanelObjectReference
            }
            val insertInstruction = getInstruction<TwoRegisterInstruction>(insertIndex)
            val classRegister = insertInstruction.registerB
            engagementPanelIdRegister = insertInstruction.registerA

            setFreeIndex(insertIndex)

            addInstructions(
                insertIndex, """
                    iget-object v$engagementPanelIdRegister, v$classRegister, $engagementPanelIdReference
                    invoke-static {v$engagementPanelIdRegister}, $EXTENSION_CLASS_DESCRIPTOR->setId(Ljava/lang/String;)V
                    """
            )
            engagementPanelIdIndex = insertIndex + 1
            engagementPanelBuilderMethod = this
        }

        engagementPanelUpdateFingerprint
            .mutableMethodOrThrow(engagementPanelBuilderFingerprint)
            .addInstruction(
                0,
                "invoke-static {}, $EXTENSION_CLASS_DESCRIPTOR->hide()V"
            )
    }
}
