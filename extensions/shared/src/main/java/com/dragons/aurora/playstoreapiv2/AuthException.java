/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.dragons.aurora.playstoreapiv2;

public class AuthException extends GooglePlayException {

    private String twoFactorUrl;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, int code) {
        super(message);
        setCode(code);
    }

    public String getTwoFactorUrl() {
        return twoFactorUrl;
    }

    public void setTwoFactorUrl(String twoFactorUrl) {
        this.twoFactorUrl = twoFactorUrl;
    }
}
