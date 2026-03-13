/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.facebook.litho;

import android.content.Context;
import android.view.ViewGroup;

/**
 * "CompileOnly" class
 * <p>
 * This class will not be included and "replaced" by the real package's class.
 */
public class ComponentHost extends ViewGroup {
    public ComponentHost(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }
}
