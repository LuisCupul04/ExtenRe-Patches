/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.video;

import android.view.Display.HdrCapabilities;

import com.extenre.extension.youtube.settings.Settings;

@SuppressWarnings("unused")
public class HDRVideoPatch {

    public static int[] disableHDRVideo(HdrCapabilities capabilities) {
        return Settings.DISABLE_HDR_VIDEO.get()
                ? new int[0]
                : capabilities.getSupportedHdrTypes();
    }
}
