/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.music.shared;

@SuppressWarnings("unused")
public final class NavigationBar {
    private static volatile int lastIndex = 0;

    /**
     * Injection point.
     */
    public static void navigationTabSelected(int index, boolean isSelected) {
        if (isSelected) {
            lastIndex = index;
        }
    }

    public static int getNavigationTabIndex() {
        return lastIndex;
    }
}
