/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.dragons.aurora.playstoreapiv2;

public interface DeviceInfoProvider {

    String getAuthUserAgentString();
    int getSdkVersion();
    int getPlayServicesVersion();
}
