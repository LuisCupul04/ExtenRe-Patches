/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.shared.trackingurlhook

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.patches.shared.extension.Constants.PATCHES_PATH
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.getReference
import app.morphe.util.indexOfFirstInstructionOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference

private const val EXTENSION_WATCH_HISTORY_CLASS_DESCRIPTOR =
    "$PATCHES_PATH/WatchHistoryPatch;"

private lateinit var trackingUrlMethod: MutableMethod

/**
 * This patch is currently used only for the 'Watch history' patch.
 * In some versions, it can be used to forcibly generate 'Watch history'.
 */
val trackingUrlHookPatch = bytecodePatch(
    name = "tracking-Url-Hook-Patch",
    description = "trackingUrlHookPatch"
) {
    execute {
        trackingUrlMethod =
            trackingUrlModelFingerprint.mutableMethodOrThrow(trackingUrlModelToStringFingerprint)
    }
}

internal fun hookTrackingUrl(
    descriptor: String
) = trackingUrlMethod.apply {
    val targetIndex = indexOfFirstInstructionOrThrow {
        opcode == Opcode.INVOKE_STATIC &&
                getReference<MethodReference>()?.name == "parse"
    } + 1
    val targetRegister = getInstruction<OneRegisterInstruction>(targetIndex).registerA

    var smaliInstruction = "invoke-static {v$targetRegister}, $descriptor"

    if (!descriptor.endsWith("V")) {
        smaliInstruction += """
                move-result-object v$targetRegister
                
                """.trimIndent()
    }

    addInstructions(
        targetIndex + 1,
        smaliInstruction
    )
}

internal fun hookWatchHistory() =
    hookTrackingUrl("$EXTENSION_WATCH_HISTORY_CLASS_DESCRIPTOR->replaceTrackingUrl(Landroid/net/Uri;)Landroid/net/Uri;")
