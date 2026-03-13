/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.dragons.aurora.playstoreapiv2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpClientAdapter {

    abstract public byte[] get(String url, Map<String, String> params, Map<String, String> headers) throws IOException;
    abstract public byte[] post(String url, Map<String, String> params, Map<String, String> headers) throws IOException;
    abstract public byte[] post(String url, byte[] body, Map<String, String> headers) throws IOException;
    abstract public String buildUrl(String url, Map<String, String> params);

    public byte[] get(String url, Map<String, String> params) throws IOException {
        return get(url, params, new HashMap<>());
    }

    public byte[] get(String url) throws IOException {
        return get(url, new HashMap<>());
    }
}
