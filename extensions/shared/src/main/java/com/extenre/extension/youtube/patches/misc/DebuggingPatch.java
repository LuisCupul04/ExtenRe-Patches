/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.misc;

import com.extenre.extension.youtube.settings.Settings;

@SuppressWarnings("unused")
public class DebuggingPatch {

    public static boolean enableWatchNextProcessingDelay() {
        return Settings.ENABLE_WATCH_NEXT_PROCESSING_DELAY.get();
    }

    public static int getWatchNextProcessingDelay(int original) {
        return Settings.ENABLE_WATCH_NEXT_PROCESSING_DELAY.get()
                ? Settings.WATCH_NEXT_PROCESSING_DELAY.get()
                : original;
    }
}
