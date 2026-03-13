/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.shared.patches;

public class AppCheckPatch {
    @SuppressWarnings("SameParameterValue")
    private static boolean classExists(String className) {
        boolean classExists = false;
        try {
            Class.forName(className);
            classExists = true;
        } catch (ClassNotFoundException ignored) {
        }
        return classExists;
    }

    private static final String MAIN_ACTIVITY_CLASS_DESCRIPTOR_YOUTUBE =
            "com.google.android.apps.youtube.app.watchwhile.MainActivity";

    public static final boolean IS_YOUTUBE =
            classExists(MAIN_ACTIVITY_CLASS_DESCRIPTOR_YOUTUBE);
}