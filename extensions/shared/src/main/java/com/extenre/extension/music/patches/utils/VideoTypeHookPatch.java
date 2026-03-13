/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.music.patches.utils;

import androidx.annotation.Nullable;

import com.extenre.extension.music.shared.VideoType;

@SuppressWarnings("unused")
public class VideoTypeHookPatch {
    /**
     * Injection point.
     */
    public static void setVideoType(@Nullable Enum<?> musicVideoType) {
        if (musicVideoType == null)
            return;

        VideoType.setFromString(musicVideoType.name());
    }
}

