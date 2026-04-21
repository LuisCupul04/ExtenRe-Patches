/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.reddit.layout.communities

import app.morphe.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.smali.ExternalLabel
import app.morphe.patches.reddit.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import app.morphe.patches.reddit.utils.extension.Constants.PATCHES_PATH
import app.morphe.patches.reddit.utils.patch.PatchList.HIDE_RECOMMENDED_COMMUNITIES_SHELF
import app.morphe.patches.reddit.utils.settings.settingsPatch
import app.morphe.patches.reddit.utils.settings.updatePatchStatus
import app.morphe.util.fingerprint.mutableMethodOrThrow

private const val EXTENSION_METHOD_DESCRIPTOR =
    "$PATCHES_PATH/RecommendedCommunitiesPatch;->hideRecommendedCommunitiesShelf()Z"

@Suppress("unused")
val recommendedCommunitiesPatch = bytecodePatch(
    name = HIDE_RECOMMENDED_COMMUNITIES_SHELF.key,
    description = "${HIDE_RECOMMENDED_COMMUNITIES_SHELF.title}: ${HIDE_RECOMMENDED_COMMUNITIES_SHELF.summary}",
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(settingsPatch)

    execute {
        communityRecommendationSectionFingerprint.mutableMethodOrThrow(
            communityRecommendationSectionParentFingerprint
        ).apply {
            addInstructionsWithLabels(
                0,
                """
                    invoke-static {}, $EXTENSION_METHOD_DESCRIPTOR
                    move-result v0
                    if-eqz v0, :off
                    return-void
                    """, ExternalLabel("off", getInstruction(0))
            )
        }

        updatePatchStatus(
            "enableRecommendedCommunitiesShelf",
            HIDE_RECOMMENDED_COMMUNITIES_SHELF
        )
    }
}
