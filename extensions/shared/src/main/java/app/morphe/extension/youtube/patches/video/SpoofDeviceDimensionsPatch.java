/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.youtube.patches.video;

import app.morphe.extension.youtube.settings.Settings;

@SuppressWarnings("unused")
public class SpoofDeviceDimensionsPatch {
    private static final boolean SPOOF = Settings.SPOOF_DEVICE_DIMENSIONS.get();

    public static int getMinHeightOrWidth(int minHeightOrWidth) {
        return SPOOF ? Math.max(128, minHeightOrWidth) : minHeightOrWidth;
    }

    public static int getMaxHeightOrWidth(int maxHeightOrWidth) {
        return SPOOF ? Math.max(8192, maxHeightOrWidth) : maxHeightOrWidth;
    }
}
