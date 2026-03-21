/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.utils;

import com.extenre.extension.youtube.shared.BottomSheetState;

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

