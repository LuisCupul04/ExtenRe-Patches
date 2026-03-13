/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package org.chromium.net;

public abstract class ExperimentalUrlRequest {
    public abstract class Builder {
        public abstract ExperimentalUrlRequest.Builder addHeader(String name, String value);
        public abstract ExperimentalUrlRequest build();
    }
}