/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.reddit.patches;

import android.view.View;

import app.morphe.extension.reddit.settings.Settings;

@SuppressWarnings("unused")
public class ToolBarButtonPatch {

    public static void hideToolBarButton(View view) {
        if (!Settings.HIDE_TOOLBAR_BUTTON.get())
            return;

        view.setVisibility(View.GONE);
    }
}
