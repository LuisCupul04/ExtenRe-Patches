/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.google.android.libraries.youtube.innertube.model.media;

public abstract class FormatStreamModel {
    public abstract String patch_getQualityName();

    public abstract int patch_getFps();
    public abstract int patch_getITag();
    public abstract int patch_getResolution();
}