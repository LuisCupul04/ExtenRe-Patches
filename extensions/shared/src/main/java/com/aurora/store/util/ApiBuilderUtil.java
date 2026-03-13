/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.aurora.store.util;

import com.aurora.store.provider.NativeDeviceInfoProvider;
import com.dragons.aurora.playstoreapiv2.DeviceInfoProvider;

public class ApiBuilderUtil {
    public static DeviceInfoProvider getDeviceInfoProvider() {
        NativeDeviceInfoProvider deviceInfoProvider = new NativeDeviceInfoProvider();
        deviceInfoProvider.setGsfVersionProvider();
        return deviceInfoProvider;
    }
}
