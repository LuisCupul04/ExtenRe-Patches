/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.music.patches.video;

import static app.morphe.extension.shared.utils.StringRef.str;
import static app.morphe.extension.shared.utils.Utils.showToastShort;

import app.morphe.extension.music.settings.Settings;
import app.morphe.extension.shared.utils.Logger;

@SuppressWarnings("unused")
public class PlaybackSpeedPatch {

    public static float getPlaybackSpeed(final float playbackSpeed) {
        try {
            return Settings.DEFAULT_PLAYBACK_SPEED.get();
        } catch (Exception ex) {
            Logger.printException(() -> "Failed to getPlaybackSpeed", ex);
        }
        return playbackSpeed;
    }

    public static void userSelectedPlaybackSpeed(final float playbackSpeed) {
        if (!Settings.REMEMBER_PLAYBACK_SPEED_LAST_SELECTED.get())
            return;

        Settings.DEFAULT_PLAYBACK_SPEED.save(playbackSpeed);

        if (!Settings.REMEMBER_PLAYBACK_SPEED_LAST_SELECTED_TOAST.get())
            return;

        showToastShort(str("extenre_remember_playback_speed_toast", playbackSpeed + "x"));
    }
}
