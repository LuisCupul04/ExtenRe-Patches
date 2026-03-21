/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.shared.innertube.utils;

import java.io.File;

import com.extenre.extension.shared.utils.Logger;
import com.extenre.extension.shared.utils.Utils;

public class J2V8Support {
    private static final boolean DEVICE_SUPPORT_J2V8;

    static {
        boolean j2v8Support = false;
        try {
            String libraryDir = Utils.getContext()
                    .getApplicationContext()
                    .getApplicationInfo()
                    .nativeLibraryDir;
            File j2v8File = new File(libraryDir + "/libj2v8.so");
            j2v8Support = j2v8File.exists();
        } catch (Exception ex) {
            Logger.printException(() -> "J2V8 native library not found", ex);
        }
        DEVICE_SUPPORT_J2V8 = j2v8Support;

        Logger.printDebug(() -> DEVICE_SUPPORT_J2V8
                ? "Device supports J2V8"
                : "Device does not support J2V8");
    }

    public static boolean supportJ2V8() {
        return DEVICE_SUPPORT_J2V8;
    }
}
