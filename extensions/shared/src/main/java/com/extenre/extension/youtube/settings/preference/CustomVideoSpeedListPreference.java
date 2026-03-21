/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.settings.preference;

import android.content.Context;
import android.util.AttributeSet;

import com.extenre.extension.shared.settings.preference.CustomDialogListPreference;
import com.extenre.extension.youtube.patches.video.CustomPlaybackSpeedPatch;

/**
 * A custom ListPreference that uses a styled custom dialog with a custom checkmark indicator.
 * Custom video speeds used by {@link CustomPlaybackSpeedPatch}.
 */
@SuppressWarnings({"unused", "deprecation"})
public final class CustomVideoSpeedListPreference extends CustomDialogListPreference {
    {
        setEntries(CustomPlaybackSpeedPatch.getEntries());
        setEntryValues(CustomPlaybackSpeedPatch.getEntryValues());
    }

    public CustomVideoSpeedListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomVideoSpeedListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomVideoSpeedListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoSpeedListPreference(Context context) {
        super(context);
    }

}
