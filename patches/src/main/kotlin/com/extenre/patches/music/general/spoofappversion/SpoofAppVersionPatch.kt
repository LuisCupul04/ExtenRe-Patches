/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.general.spoofappversion

import com.extenre.patcher.patch.bytecodePatch
import com.extenre.patcher.patch.resourcePatch
import com.extenre.patches.music.utils.compatibility.Constants.YOUTUBE_MUSIC_PACKAGE_NAME
import com.extenre.patches.music.utils.extension.Constants.GENERAL_CLASS_DESCRIPTOR
import com.extenre.patches.music.utils.extension.Constants.PATCH_STATUS_CLASS_DESCRIPTOR
import com.extenre.patches.music.utils.extension.sharedExtensionPatch
import com.extenre.patches.music.utils.patch.PatchList.SPOOF_APP_VERSION
import com.extenre.patches.music.utils.playservice.is_6_36_or_greater
import com.extenre.patches.music.utils.playservice.is_6_43_or_greater
import com.extenre.patches.music.utils.playservice.is_7_25_or_greater
import com.extenre.patches.music.utils.playservice.versionCheckPatch
import com.extenre.patches.music.utils.settings.CategoryType
import com.extenre.patches.music.utils.settings.ResourceUtils.updatePatchStatus
import com.extenre.patches.music.utils.settings.addPreferenceWithIntent
import com.extenre.patches.music.utils.settings.addSwitchPreference
import com.extenre.patches.music.utils.settings.settingsPatch
import com.extenre.patches.shared.spoof.appversion.baseSpoofAppVersionPatch
import com.extenre.patches.shared.spoof.watchnext.spoofAppVersionWatchNextPatch
import com.extenre.util.Utils.printWarn
import com.extenre.util.appendAppVersion
import com.extenre.util.findMethodOrThrow
import com.extenre.util.returnEarly

private val spoofAppVersionWatchNextPatch = spoofAppVersionWatchNextPatch(
    block = {
        dependsOn(
            sharedExtensionPatch,
            versionCheckPatch
        )
    },
    patchRequired = {
        is_7_25_or_greater
    },
    availabilityDescriptor = "$GENERAL_CLASS_DESCRIPTOR->spoofWatchNextEndpointAppVersionEnabled()Z",
    appVersionDescriptor = "$GENERAL_CLASS_DESCRIPTOR->getWatchNextEndpointVersionOverride()Ljava/lang/String;"
)

private val spoofAppVersionBytecodePatch = bytecodePatch(
    description = "spoofAppVersionBytecodePatch"
) {
    dependsOn(
        baseSpoofAppVersionPatch("$GENERAL_CLASS_DESCRIPTOR->getVersionOverride(Ljava/lang/String;)Ljava/lang/String;"),
        versionCheckPatch,
    )

    execute {
        if (!is_6_36_or_greater) {
            return@execute
        }

        val defaultVersionString = if (is_7_25_or_greater)
            "6.42.55" else "6.35.52"

        findMethodOrThrow(PATCH_STATUS_CLASS_DESCRIPTOR) {
            name == "SpoofAppVersionDefaultString"
        }.returnEarly(defaultVersionString)
    }
}

@Suppress("unused")
val spoofAppVersionPatch = resourcePatch(
    // SPOOF_APP_VERSION.title,
    // SPOOF_APP_VERSION.summary,
) {
    compatibleWith(
        YOUTUBE_MUSIC_PACKAGE_NAME(
            "6.42.55",
            "6.51.53",
            "7.16.53",
            "7.25.53",
            "8.12.54",
            "8.28.54",
            "8.30.54",
            "9.05.51",
        ),
    )

    dependsOn(
        spoofAppVersionBytecodePatch,
        spoofAppVersionWatchNextPatch,
        settingsPatch,
        versionCheckPatch,
    )

    execute {
        if (!is_6_36_or_greater) {
            printWarn("\"${SPOOF_APP_VERSION.title}\" is not supported in this version. Use YouTube Music 6.36.54 or later.")
            return@execute
        }
        if (is_7_25_or_greater) {
            appendAppVersion("7.17.52")
        }
        if (is_6_43_or_greater) {
            appendAppVersion("6.42.55")
        }
        if (!is_7_25_or_greater) {
            appendAppVersion("6.35.52")
        }

        addSwitchPreference(
            CategoryType.GENERAL,
            "extenre_spoof_app_version",
            "false"
        )
        addPreferenceWithIntent(
            CategoryType.GENERAL,
            "extenre_spoof_app_version_target",
            "extenre_spoof_app_version"
        )

        updatePatchStatus(SPOOF_APP_VERSION)

    }
}
