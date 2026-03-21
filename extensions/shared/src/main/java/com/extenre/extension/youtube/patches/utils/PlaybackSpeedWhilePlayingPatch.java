/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.utils;

import com.extenre.extension.shared.utils.Logger;
import com.extenre.extension.youtube.shared.EngagementPanel;
import com.extenre.extension.youtube.shared.PlayerType;
import com.extenre.extension.youtube.shared.ShortsPlayerState;

@SuppressWarnings("unused")
public class PlaybackSpeedWhilePlayingPatch {
    private static final float DEFAULT_YOUTUBE_PLAYBACK_SPEED = 1.0f;

    public static boolean playbackSpeedChanged(float playbackSpeed) {
        if (playbackSpeed == DEFAULT_YOUTUBE_PLAYBACK_SPEED) {
            if (PlayerType.getCurrent().isMaximizedOrFullscreenOrPiP()
                    // Since RVX has a default playback speed setting for Shorts,
                    // Playback speed reset should also be prevented in Shorts.
                    || ShortsPlayerState.getCurrent().isOpen() && EngagementPanel.isOpen()) {
                Logger.printDebug(() -> "Ignore changing playback speed, as it is invalid request");

                return true;
            }
        }

        return false;
    }

}


