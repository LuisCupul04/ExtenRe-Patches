/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.shared.settings.preference;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Extended ColorPickerPreference that enables the opacity slider for color selection.
 */
@SuppressWarnings("unused")
public class ColorPickerWithOpacitySliderPreference extends ColorPickerPreference {

    public ColorPickerWithOpacitySliderPreference(Context context) {
        super(context);
        init();
    }

    public ColorPickerWithOpacitySliderPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorPickerWithOpacitySliderPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Initialize the preference with opacity slider enabled.
     */
    private void init() {
        // Enable the opacity slider for alpha channel support.
        setOpacitySliderEnabled(true);
    }
}
