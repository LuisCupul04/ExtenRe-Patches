/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.fix.attributes

import com.extenre.patcher.extensions.InstructionExtensions.getInstruction
import com.extenre.patcher.extensions.InstructionExtensions.removeInstructions
import com.extenre.patcher.extensions.InstructionExtensions.replaceInstruction
import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patches.youtube.utils.playservice.is_19_25_or_greater
import com.extenre.patches.youtube.utils.playservice.is_20_04_or_greater
import com.extenre.patches.youtube.utils.playservice.versionCheckPatch
import com.extenre.patches.youtube.utils.resourceid.sharedResourceIdPatch
import com.extenre.util.fingerprint.injectLiteralInstructionBooleanCall
import com.extenre.util.fingerprint.matchOrThrow
import com.extenre.util.referenceMatchesOrThrow
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction

val themeAttributesPatch = bytecodePatch(
    description = "themeAttributesPatch"
) {
    dependsOn(
        sharedResourceIdPatch,
        versionCheckPatch,
    )

    execute {
        if (!is_19_25_or_greater) {
            return@execute
        }

        /**
         * There is a warning in the logcat of an unpatched YouTube 19.25.39+:
         *
         * Drawable com.google.android.youtube:drawable/yt_outline_moon_z_vd_theme_24 has unresolved theme attributes! Consider using Resources.getDrawable(int, Theme) or Context.getDrawable(int).
         * java.lang.RuntimeException
         * 	at android.content.res.Resources.getDrawable(Resources.java:857)
         *
         * According to [stackoverflow](https://stackoverflow.com/questions/28932306/logcat-says-resource-has-unresolved-theme-attributes),
         * Replace [Resources.getDrawable(int)] with [Context.getDrawable(int)].
         */
        setSleepTimerDrawableFingerprint.matchOrThrow().let {
            it.method.apply {
                val getResourcesIndex = it.patternMatch!!.startIndex
                val getDrawableIndex = it.patternMatch!!.endIndex

                // Verify that the correct pattern has been found.
                referenceMatchesOrThrow(
                    getResourcesIndex,
                    "Landroid/content/Context;->getResources()Landroid/content/res/Resources;"
                )
                referenceMatchesOrThrow(
                    getDrawableIndex,
                    "Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;"
                )

                val contextRegister =
                    getInstruction<FiveRegisterInstruction>(getResourcesIndex).registerC
                val identifierRegister =
                    getInstruction<FiveRegisterInstruction>(getDrawableIndex).registerD

                replaceInstruction(
                    getDrawableIndex,
                    "invoke-virtual {v$contextRegister, v$identifierRegister}, " +
                            "Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;"
                )
                removeInstructions(getResourcesIndex, 2)
            }
        }

        /**
         * If playback starts late, the app may force close when 'Stats for nerds' is open.
         * (Unpatched YouTube issue)
         */
        if (is_20_04_or_greater) {
            statsForNerdsFeatureFlagFingerprint.injectLiteralInstructionBooleanCall(
                STATS_FOR_NERDS_FEATURE_FLAG,
                "0x0"
            )
        }

    }
}
