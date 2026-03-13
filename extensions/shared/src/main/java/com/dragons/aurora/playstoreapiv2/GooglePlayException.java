/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.dragons.aurora.playstoreapiv2;

import java.io.IOException;

public class GooglePlayException extends IOException {

    protected int code;

    public GooglePlayException(String message) {
        super(message);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
