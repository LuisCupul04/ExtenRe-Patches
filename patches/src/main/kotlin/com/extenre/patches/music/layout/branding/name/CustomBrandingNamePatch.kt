/*
 * Copyright (C) 2026 LuisCupul04
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2022 ReVanced LLC
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.layout.branding.name

import com.extenre.patcher.patch.resourcePatch
import com.extenre.patcher.patch.stringOption
import com.extenre.patches.music.utils.compatibility.Constants.COMPATIBLE_PACKAGE
import com.extenre.patches.music.utils.patch.PatchList.CUSTOM_BRANDING_NAME_FOR_YOUTUBE_MUSIC
import com.extenre.patches.music.utils.settings.ResourceUtils.updatePatchStatus
import com.extenre.patches.music.utils.settings.settingsPatch
import com.extenre.util.removeStringsElements
import com.extenre.util.valueOrThrow

private const val APP_NAME_NOTIFICATION = "YTM ExtenRe"
private const val APP_NAME_LAUNCHER = "YTM ExtenRe"

@Suppress("unused")
val customBrandingNamePatch = resourcePatch(
    CUSTOM_BRANDING_NAME_FOR_YOUTUBE_MUSIC.title,
    CUSTOM_BRANDING_NAME_FOR_YOUTUBE_MUSIC.summary,
) {
    compatibleWith(COMPATIBLE_PACKAGE)

    dependsOn(settingsPatch)

    val appNameNotificationOption = stringOption(
        key = "appNameNotification",
        default = APP_NAME_LAUNCHER,
        values = mapOf(
            "YT ExtenRe" to APP_NAME_NOTIFICATION,
            "YT ExtenRe" to APP_NAME_LAUNCHER,
            "RVX" to "RVX",
            "ReVanced Extended" to "ReVanced Extended",
            "YouTube Music" to "YouTube Music",
            "YT Music" to "YT Music",
            "YTM" to "YTM",
            "YTM RE" to "YTM RE",
            "YTM EXTEN" to "YTM EXTEN",
            "YTM ExtenRe" to "YTM ExtenRe",
            "YTM EXRE" to "YTM EXRE",
            "YTM NEKO" to "YTM NEKO",
            "YTM KITSUNE" to "YTM KITSUNE",
        ),
        title = "App name in notification panel",
        description = "The name of the app as it appears in the notification panel.",
        required = true
    )

    val appNameLauncherOption = stringOption(
        key = "appNameLauncher",
        default = APP_NAME_LAUNCHER,
        values = mapOf(
            "YT ExtenRe" to APP_NAME_NOTIFICATION,
            "YT ExtenRe" to APP_NAME_LAUNCHER,
            "RVX" to "RVX",
            "ReVanced Extended" to "ReVanced Extended",
            "YouTube Music" to "YouTube Music",
            "YT Music" to "YT Music",
            "YTM" to "YTM",
            "YTM RE" to "YTM RE",
            "YTM EXTEN" to "YTM EXTEN",
            "YTM ExtenRe" to "YTM ExtenRe",
            "YTM EXRE" to "YTM EXRE",
            "YTM NEKO" to "YTM NEKO",
            "YTM KITSUNE" to "YTM KITSUNE",
        ),
        title = "App name in launcher",
        description = "The name of the app as it appears in the launcher.",
        required = true
    )

    execute {
        // Check patch options first.
        val notificationName = appNameNotificationOption
            .valueOrThrow()
        val launcherName = appNameLauncherOption
            .valueOrThrow()

        removeStringsElements(
            arrayOf("app_launcher_name", "app_name")
        )

        document("res/values/strings.xml").use { document ->
            mapOf(
                "app_name" to notificationName,
                "app_launcher_name" to launcherName
            ).forEach { (k, v) ->
                val stringElement = document.createElement("string")

                stringElement.setAttribute("name", k)
                stringElement.textContent = v

                document.getElementsByTagName("resources").item(0)
                    .appendChild(stringElement)
            }
        }

        updatePatchStatus(CUSTOM_BRANDING_NAME_FOR_YOUTUBE_MUSIC)

    }
}
