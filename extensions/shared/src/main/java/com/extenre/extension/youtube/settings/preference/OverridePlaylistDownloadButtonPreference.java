/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.settings.preference;

import static com.extenre.extension.shared.utils.StringRef.str;
import static com.extenre.extension.youtube.utils.ExtendedUtils.IS_19_29_OR_GREATER;
import static com.extenre.extension.youtube.utils.ExtendedUtils.isSpoofingToLessThan;

import android.content.Context;
import android.preference.SwitchPreference;
import android.util.AttributeSet;

import com.extenre.extension.youtube.patches.utils.PatchStatus;

@SuppressWarnings({"deprecation", "unused"})
public class OverridePlaylistDownloadButtonPreference extends SwitchPreference {
    {
        String summaryOn = "extenre_override_playlist_download_button_summary_on";
        if (IS_19_29_OR_GREATER) {
            if (!PatchStatus.SpoofAppVersion()) {
                summaryOn = "extenre_override_playlist_download_button_summary_on_disclaimer_1";
            } else if (!isSpoofingToLessThan("19.29.00")) {
                summaryOn = "extenre_override_playlist_download_button_summary_on_disclaimer_2";
            }
        }

        setSummaryOn(str(summaryOn));
    }

    public OverridePlaylistDownloadButtonPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public OverridePlaylistDownloadButtonPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OverridePlaylistDownloadButtonPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverridePlaylistDownloadButtonPreference(Context context) {
        super(context);
    }
}
