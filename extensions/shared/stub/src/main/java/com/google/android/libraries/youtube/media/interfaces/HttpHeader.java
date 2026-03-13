/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.google.android.libraries.youtube.media.interfaces;

public record HttpHeader(String key, String value) {

    @Override
    public String toString() {
        return "HttpHeader{key=" + this.key +
                ",value=" + this.value +
                "}";
    }
}