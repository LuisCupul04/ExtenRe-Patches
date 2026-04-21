/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.music.patches.utils;

import static app.morphe.extension.music.utils.ExtendedUtils.IS_7_25_OR_GREATER;

@SuppressWarnings("unused")
public class PatchStatus {
    public static boolean DarkTheme() {
        // Replace this with true if the 'Dark theme' patch succeeds
        return false;
    }

    public static String SpoofAppVersionDefaultString() {
        return IS_7_25_OR_GREATER ? "6.42.55" : "6.35.52";
    }
}
