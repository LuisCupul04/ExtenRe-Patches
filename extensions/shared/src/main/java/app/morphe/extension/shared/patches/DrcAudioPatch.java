/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.shared.patches;

import app.morphe.extension.shared.settings.BaseSettings;

@SuppressWarnings("unused")
public class DrcAudioPatch {
    private static final boolean DISABLE_DRC_AUDIO =
            BaseSettings.DISABLE_DRC_AUDIO.get();

    public static boolean disableDrcAudio() {
        return DISABLE_DRC_AUDIO;
    }

    public static boolean disableDrcAudioFeatureFlag(boolean original) {
        return !DISABLE_DRC_AUDIO && original;
    }
}
