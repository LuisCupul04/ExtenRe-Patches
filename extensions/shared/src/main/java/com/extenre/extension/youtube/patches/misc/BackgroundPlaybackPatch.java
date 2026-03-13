/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.misc;

import com.extenre.extension.shared.settings.BooleanSetting;
import com.extenre.extension.youtube.settings.Settings;
import com.extenre.extension.youtube.shared.PlayerType;
import com.extenre.extension.youtube.shared.ShortsPlayerState;

@SuppressWarnings("unused")
public class BackgroundPlaybackPatch {
    private static final BooleanSetting DISABLE_SHORTS_BACKGROUND_PLAYBACK =
            Settings.DISABLE_SHORTS_BACKGROUND_PLAYBACK;

    /**
     * Injection point.
     */
    public static boolean isBackgroundPlaybackAllowed(boolean original) {
        if (original) return true;
        return ShortsPlayerState.getCurrent().isClosed() &&
                // 1. Shorts background playback is enabled.
                // 2. Autoplay in feed is turned on.
                // 3. Play Shorts from feed.
                // 4. Media controls appear in status bar.
                // (For unpatched YouTube with Premium accounts, media controls do not appear in the status bar)
                //
                // This is just a visual bug and does not affect Shorts background play in any way.
                // To fix this, just check PlayerType.
                PlayerType.getCurrent() != PlayerType.INLINE_MINIMAL;
    }

    /**
     * Injection point.
     */
    public static boolean isBackgroundShortsPlaybackAllowed(boolean original) {
        return !DISABLE_SHORTS_BACKGROUND_PLAYBACK.get();
    }

}
