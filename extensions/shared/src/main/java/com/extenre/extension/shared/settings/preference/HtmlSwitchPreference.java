/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.shared.settings.preference;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

import android.content.Context;
import android.preference.SwitchPreference;
import android.text.Html;
import android.util.AttributeSet;

/**
 * Allows using basic html for the summary text.
 */
@SuppressWarnings({"unused", "deprecation"})
public class HtmlSwitchPreference extends SwitchPreference {
    {
        setSummaryOn(Html.fromHtml(getSummaryOn().toString(), FROM_HTML_MODE_COMPACT));
        setSummaryOff(Html.fromHtml(getSummaryOff().toString(), FROM_HTML_MODE_COMPACT));
    }

    public HtmlSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public HtmlSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HtmlSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlSwitchPreference(Context context) {
        super(context);
    }
}