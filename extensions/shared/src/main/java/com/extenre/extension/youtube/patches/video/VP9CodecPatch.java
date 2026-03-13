/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.video;

import com.extenre.extension.youtube.settings.Settings;

@SuppressWarnings("unused")
public class VP9CodecPatch {

    public static boolean disableVP9Codec() {
        return !Settings.DISABLE_VP9_CODEC.get();
    }
}
