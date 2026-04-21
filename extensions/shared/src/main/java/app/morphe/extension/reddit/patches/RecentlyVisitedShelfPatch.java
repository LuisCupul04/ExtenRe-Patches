/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.reddit.patches;

import java.util.Collections;
import java.util.List;

import app.morphe.extension.reddit.settings.Settings;

@SuppressWarnings("unused")
public final class RecentlyVisitedShelfPatch {

    public static List<?> hideRecentlyVisitedShelf(List<?> list) {
        return Settings.HIDE_RECENTLY_VISITED_SHELF.get() ? Collections.emptyList() : list;
    }
}
