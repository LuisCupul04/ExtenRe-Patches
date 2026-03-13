/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.fix.streamingdata

import com.extenre.patches.shared.spoof.streamingdata.EXTENSION_CLASS_DESCRIPTOR
import com.extenre.patches.shared.spoof.streamingdata.EXTENSION_RELOAD_VIDEO_BUTTON_CLASS_DESCRIPTOR
import com.extenre.patches.shared.spoof.streamingdata.spoofStreamingDataPatch
import com.extenre.patches.shared.spoof.useragent.baseSpoofUserAgentPatch
import com.extenre.patches.youtube.player.overlaybuttons.overlayButtonsPatch
import com.extenre.patches.youtube.utils.compatibility.Constants.YOUTUBE_PACKAGE_NAME
import com.extenre.patches.youtube.utils.dismiss.dismissPlayerHookPatch
import com.extenre.patches.youtube.utils.playercontrols.addTopControl
import com.extenre.patches.youtube.utils.playercontrols.injectControl
import com.extenre.patches.youtube.utils.playercontrols.playerControlsPatch
import com.extenre.patches.youtube.utils.playservice.is_19_34_or_greater
import com.extenre.patches.youtube.utils.playservice.is_19_50_or_greater
import com.extenre.patches.youtube.utils.playservice.is_20_10_or_greater
import com.extenre.patches.youtube.utils.playservice.is_20_14_or_greater
import com.extenre.patches.youtube.utils.playservice.versionCheckPatch
import com.extenre.patches.youtube.utils.request.buildRequestPatch
import com.extenre.patches.youtube.utils.request.hookBuildRequest
import com.extenre.patches.youtube.utils.request.hookBuildRequestUrl
import com.extenre.patches.youtube.utils.settings.ResourceUtils.addPreference
import com.extenre.patches.youtube.utils.settings.settingsPatch
import com.extenre.patches.youtube.video.information.videoInformationPatch
import com.extenre.patches.youtube.video.playerresponse.Hook
import com.extenre.patches.youtube.video.playerresponse.addPlayerResponseMethodHook
import com.extenre.patches.youtube.video.videoid.videoIdPatch
import com.extenre.util.getStringOptionValue
import com.extenre.util.lowerCaseOrThrow

val spoofStreamingDataPatch = spoofStreamingDataPatch(
    block = {
        dependsOn(
            settingsPatch,
            versionCheckPatch,
            baseSpoofUserAgentPatch(YOUTUBE_PACKAGE_NAME),
            buildRequestPatch,
            playerControlsPatch,
            videoIdPatch,
            videoInformationPatch,
            dismissPlayerHookPatch,
        )
    },
    isYouTube = {
        true
    },
    outlineIcon = {
        val iconType = overlayButtonsPatch
            .getStringOptionValue("iconType")
            .lowerCaseOrThrow()
        iconType == "thin"
    },
    fixMediaFetchHotConfigChanges = {
        is_19_34_or_greater
    },
    fixMediaFetchHotConfigAlternativeChanges = {
        // In 20.14 the flag was merged with 19.50 start playback flag.
        is_20_10_or_greater && !is_20_14_or_greater
    },
    fixParsePlaybackResponseFeatureFlag = {
        is_19_50_or_greater
    },
    executeBlock = {

        // region Get replacement streams at player requests.

        hookBuildRequest("$EXTENSION_CLASS_DESCRIPTOR->fetchStreams(Ljava/lang/String;Ljava/util/Map;)V")
        hookBuildRequestUrl("$EXTENSION_CLASS_DESCRIPTOR->blockGetAttRequest(Ljava/lang/String;)Ljava/lang/String;")

        // endregion

        addPlayerResponseMethodHook(
            Hook.PlayerParameterBeforeVideoId(
                "$EXTENSION_CLASS_DESCRIPTOR->newPlayerResponseParameter(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;"
            )
        )

        // region Player buttons

        injectControl(EXTENSION_RELOAD_VIDEO_BUTTON_CLASS_DESCRIPTOR)

        // endregion

        addPreference(
            arrayOf(
                "SETTINGS: SPOOF_STREAMING_DATA"
            )
        )
    },
    finalizeBlock = {
        addTopControl(
            "youtube/spoof/shared",
            "@+id/extenre_reload_video_button",
            "@+id/extenre_reload_video_button"
        )
    }
)
