/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("CONTEXT_RECEIVERS_DEPRECATED")

package app.morphe.patches.youtube.utils.castbutton

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.extensions.InstructionExtensions.removeInstruction
import app.morphe.patcher.patch.BytecodePatchContext
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.mutableTypes.MutableMethod
import app.morphe.patches.youtube.utils.extension.Constants.GENERAL_CLASS_DESCRIPTOR
import app.morphe.patches.youtube.utils.extension.Constants.PATCH_STATUS_CLASS_DESCRIPTOR
import app.morphe.patches.youtube.utils.extension.Constants.PLAYER_CLASS_DESCRIPTOR
import app.morphe.patches.youtube.utils.extension.Constants.UTILS_PATH
import app.morphe.patches.youtube.utils.resourceid.sharedResourceIdPatch
import app.morphe.util.findMethodOrThrow
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.getReference
import app.morphe.util.indexOfFirstInstructionOrThrow
import app.morphe.util.mutableClassDefBy
import app.morphe.util.updatePatchStatus
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.util.MethodUtil

private const val EXTENSION_CLASS_DESCRIPTOR =
    "$UTILS_PATH/CastButtonPatch;"

private lateinit var playerButtonMethod: MutableMethod
private lateinit var toolbarMenuItemInitializeMethod: MutableMethod
private lateinit var toolbarMenuItemVisibilityMethod: MutableMethod

val castButtonPatch = bytecodePatch(
    name = "cast-Button-Patch",
    description = "castButtonPatch"
) {
    dependsOn(sharedResourceIdPatch)

    execute {
        toolbarMenuItemInitializeMethod = menuItemInitializeFingerprint.mutableMethodOrThrow()
        toolbarMenuItemVisibilityMethod =
            menuItemVisibilityFingerprint.mutableMethodOrThrow(menuItemInitializeFingerprint)

        playerButtonMethod = playerButtonFingerprint.mutableMethodOrThrow()

        // ✅ Morphe: obtener método y clase mutable correctamente
        val method = findMethodOrThrow("Landroidx/mediarouter/app/MediaRouteButton;") {
            name == "setVisibility"
        }
        // ✅ Morphe: usar classDefs en lugar de classes
        val classDef = classDefs.find { it.type == "Landroidx/mediarouter/app/MediaRouteButton;" }
            ?: throw PatchException("Class not found: Landroidx/mediarouter/app/MediaRouteButton;")
        // ✅ Morphe: usar mutableClassDefBy en lugar de proxy
        val mutableMethod = mutableClassDefBy(classDef).methods.first {
            MethodUtil.methodSignaturesMatch(it, method)
        }
        mutableMethod.addInstructions(
            0, """
                    invoke-static {p1}, $EXTENSION_CLASS_DESCRIPTOR->hideCastButton(I)I
                    move-result p1
                    """
        )
    }
}

context(BytecodePatchContext)
internal fun hookPlayerCastButton() {
    playerButtonMethod.apply {
        val index = indexOfFirstInstructionOrThrow {
            getReference<MethodReference>()?.name == "setVisibility"
        }
        val instruction = getInstruction<FiveRegisterInstruction>(index)
        val viewRegister = instruction.registerC
        val visibilityRegister = instruction.registerD
        val reference = getInstruction<ReferenceInstruction>(index).reference

        addInstructions(
            index + 1, """
                    invoke-static {v$visibilityRegister}, $PLAYER_CLASS_DESCRIPTOR->hideCastButton(I)I
                    move-result v$visibilityRegister
                    invoke-virtual {v$viewRegister, v$visibilityRegister}, $reference
                    """
        )
        removeInstruction(index)
    }
    updatePatchStatus(PATCH_STATUS_CLASS_DESCRIPTOR, "PlayerButtons")
}

context(BytecodePatchContext)
internal fun hookToolBarCastButton() {
    toolbarMenuItemInitializeMethod.apply {
        val index = indexOfFirstInstructionOrThrow {
            getReference<MethodReference>()?.name == "setShowAsAction"
        } + 1
        addInstruction(
            index,
            "invoke-static {p1}, $GENERAL_CLASS_DESCRIPTOR->hideCastButton(Landroid/view/MenuItem;)V"
        )
    }
    toolbarMenuItemVisibilityMethod.addInstructions(
        0, """
                invoke-static {p1}, $GENERAL_CLASS_DESCRIPTOR->hideCastButton(Z)Z
                move-result p1
                """
    )
    updatePatchStatus(PATCH_STATUS_CLASS_DESCRIPTOR, "ToolBarComponents")
}
