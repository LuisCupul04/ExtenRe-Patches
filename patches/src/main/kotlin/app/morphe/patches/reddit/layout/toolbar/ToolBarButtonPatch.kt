/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.reddit.layout.toolbar

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.reddit.utils.extension.Constants.PATCHES_PATH
import app.morphe.patches.reddit.utils.patch.PatchList.HIDE_TOOLBAR_BUTTON
import app.morphe.patches.reddit.utils.settings.settingsPatch
import app.morphe.patches.reddit.utils.settings.updatePatchStatus
import app.morphe.util.fingerprint.matchOrThrow
import app.morphe.util.indexOfFirstInstructionOrThrow
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.util.MethodUtil

private const val EXTENSION_METHOD_DESCRIPTOR =
    "$PATCHES_PATH/ToolBarButtonPatch;->hideToolBarButton(Landroid/view/View;)V"

@Suppress("unused")
@Deprecated("This patch is deprecated until Reddit adds a button like r/place or Reddit recap button to the toolbar.")
val toolBarButtonPatch = bytecodePatch(
    name = HIDE_TOOLBAR_BUTTON.key,
    description = "${HIDE_TOOLBAR_BUTTON.title}: ${HIDE_TOOLBAR_BUTTON.summary}",
) {
    // compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(settingsPatch)

    execute {
        val homePagerScreenMatch = homePagerScreenFingerprint.matchOrThrow()
        val method = homePagerScreenMatch.method
        val classDef = homePagerScreenMatch.classDef
        val mutableMethod = proxy(classDef).mutableClass.methods.first {
            MethodUtil.methodSignaturesMatch(it, method)
        }
        mutableMethod.apply {
            val stringIndex = homePagerScreenMatch.stringMatches!!.first().index
            val insertIndex = indexOfFirstInstructionOrThrow(stringIndex, Opcode.CHECK_CAST)
            val insertRegister =
                getInstruction<OneRegisterInstruction>(insertIndex).registerA

            addInstruction(
                insertIndex,
                "invoke-static {v$insertRegister}, $EXTENSION_METHOD_DESCRIPTOR"
            )
        }

        updatePatchStatus(
            "enableToolBarButton",
            HIDE_TOOLBAR_BUTTON
        )
    }
}
