/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.reddit.layout.trendingtoday

import app.morphe.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.smali.ExternalLabel
import app.morphe.patches.reddit.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.morphe.patches.reddit.utils.extension.Constants.PATCHES_PATH
import app.morphe.patches.reddit.utils.patch.PatchList.HIDE_TRENDING_TODAY_SHELF
import app.morphe.patches.reddit.utils.settings.settingsPatch
import app.morphe.patches.reddit.utils.settings.updatePatchStatus
import app.morphe.util.fingerprint.matchOrThrow
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.indexOfFirstInstructionReversedOrThrow
import app.morphe.util.mutableClassDefBy
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction
import com.android.tools.smali.dexlib2.util.MethodUtil

private const val EXTENSION_CLASS_DESCRIPTOR =
    "$PATCHES_PATH/TrendingTodayShelfPatch;"

@Suppress("unused")
val trendingTodayShelfPatch = bytecodePatch(
    name = HIDE_TRENDING_TODAY_SHELF.key,
    description = "${HIDE_TRENDING_TODAY_SHELF.title}: ${HIDE_TRENDING_TODAY_SHELF.summary}",
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(settingsPatch)

    execute {

        // region patch for hide trending today title.

        val trendingTodayTitleMatch = trendingTodayTitleFingerprint.matchOrThrow()
        val titleMethod = trendingTodayTitleMatch.method
        val titleClassDef = trendingTodayTitleMatch.classDef
        // ✅ Morphe: usar mutableClassDefBy en lugar de proxy
        val titleMutableMethod = mutableClassDefBy(titleClassDef).methods.first {
            MethodUtil.methodSignaturesMatch(it, titleMethod)
        }
        titleMutableMethod.apply {
            // ✅ Morphe: stringMatches no es nullable, quitar el operador !!
            val stringIndex = trendingTodayTitleMatch.stringMatches.first().index
            val relativeIndex =
                indexOfFirstInstructionReversedOrThrow(stringIndex, Opcode.AND_INT_LIT8)
            val insertIndex = indexOfFirstInstructionReversedOrThrow(
                relativeIndex + 1,
                Opcode.MOVE_OBJECT_FROM16
            )
            val insertRegister = getInstruction<TwoRegisterInstruction>(insertIndex).registerA
            val jumpOpcode = if (returnType == "V") Opcode.RETURN_VOID else Opcode.SGET_OBJECT
            val jumpIndex = indexOfFirstInstructionReversedOrThrow(jumpOpcode)

            addInstructionsWithLabels(
                insertIndex, """
                        invoke-static {}, $EXTENSION_CLASS_DESCRIPTOR->hideTrendingTodayShelf()Z
                        move-result v$insertRegister
                        if-nez v$insertRegister, :hidden
                        """, ExternalLabel("hidden", getInstruction(jumpIndex))
            )
        }

        // endregion

        // region patch for hide trending today contents.

        trendingTodayItemFingerprint.mutableMethodOrThrow().addInstructionsWithLabels(
            0, """
                invoke-static {}, $EXTENSION_CLASS_DESCRIPTOR->hideTrendingTodayShelf()Z
                move-result v0
                if-eqz v0, :ignore
                return-void
                :ignore
                nop
                """
        )

        // endregion

        updatePatchStatus(
            "enableTrendingTodayShelf",
            HIDE_TRENDING_TODAY_SHELF
        )
    }
}
