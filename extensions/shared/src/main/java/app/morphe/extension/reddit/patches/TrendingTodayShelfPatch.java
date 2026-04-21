/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.reddit.patches;

import app.morphe.extension.reddit.settings.Settings;

@SuppressWarnings("unused")
public final class TrendingTodayShelfPatch {

    public static boolean hideTrendingTodayShelf() {
        return Settings.HIDE_TRENDING_TODAY_SHELF.get();
    }

}
