/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.settings.preference;

import static com.extenre.extension.shared.utils.StringRef.str;

import android.content.Context;
import android.preference.SwitchPreference;
import android.util.AttributeSet;

import com.extenre.extension.shared.patches.spoof.SpoofStreamingDataPatch;

@SuppressWarnings({"deprecation", "unused"})
public class ForceOriginalAudioSwitchPreference extends SwitchPreference {
    {
        String summaryOn = SpoofStreamingDataPatch.multiAudioTrackAvailable()
                ? "extenre_disable_auto_audio_tracks_summary_on"
                : "extenre_disable_auto_audio_tracks_summary_on_disclaimer";
        setSummaryOn(str(summaryOn));
        setSummaryOff(str("extenre_disable_auto_audio_tracks_summary_off"));
    }

    public ForceOriginalAudioSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ForceOriginalAudioSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ForceOriginalAudioSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForceOriginalAudioSwitchPreference(Context context) {
        super(context);
    }
}
