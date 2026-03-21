/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package org.chromium.net;

public abstract class UrlRequest {
    public abstract class Builder {
        public abstract UrlRequest.Builder addHeader(String name, String value);
        public abstract UrlRequest build();
    }
}
