/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.youtube.patches.utils;

import app.morphe.extension.youtube.shared.BottomSheetState;

@SuppressWarnings("unused")
public class BottomSheetHookPatch {
    /**
     * Injection point.
     */
    public static void onAttachedToWindow() {
        BottomSheetState.set(BottomSheetState.OPEN);
    }

    /**
     * Injection point.
     */
    public static void onDetachedFromWindow() {
        BottomSheetState.set(BottomSheetState.CLOSED);
    }
}

