/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package org.chromium.net.impl;

import org.chromium.net.UrlRequest;

public abstract class CronetUrlRequest extends UrlRequest {

    /**
     * Method is added by patch.
     */
    public abstract String getHookedUrl();
}
