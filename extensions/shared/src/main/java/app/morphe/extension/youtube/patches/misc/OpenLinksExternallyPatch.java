/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.youtube.patches.misc;

import app.morphe.extension.youtube.settings.Settings;

@SuppressWarnings("unused")
public class OpenLinksExternallyPatch {

    // renamed from 'enableExternalBrowser'
    public static String openLinksExternally(final String original) {
        if (!Settings.OPEN_LINKS_EXTERNALLY.get())
            return original;

        return "";
    }
}
