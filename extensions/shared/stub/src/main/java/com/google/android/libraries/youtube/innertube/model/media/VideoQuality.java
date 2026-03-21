/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.google.android.libraries.youtube.innertube.model.media;

/**
 * From YouTube 17.34.36 to the latest version,
 * the fields of the class have the same name, type and access flag.
 */
public abstract class VideoQuality implements Comparable<VideoQuality> {
    // Quality value of the current video in human readable, such as '1440', '1080', '720'...
    // public final int a;
    // Quality label of the current video in human readable, such as '2160p60 HDR', '1080p Premium', '720p60', '720s'...
    // public final String b;
    //
    // public VideoQuality(int a, String b) {
    //     this.a = a;
    //     this.b = b;
    // }

    public abstract String patch_getQualityName();

    public abstract int patch_getResolution();
}