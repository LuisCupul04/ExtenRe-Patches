/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.dragons.aurora.playstoreapiv2;

public class PackageNameUtil {
    private static String packageName = "";

    public static String getGmsCorePackageName() {
        return getGmsCorePackageName("com");
    }

    @SuppressWarnings("SameParameterValue")
    private static String getGmsCorePackageName(String prefix) {
        if (packageName.isEmpty()) {
            packageName = prefix + ".google.android.gms";
        }
        return packageName;
    }
}