/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.shared.webview

import app.morphe.util.fingerprint.legacyFingerprint

internal val webViewHostActivityOnCreateFingerprint = legacyFingerprint(
    name = "webViewHostActivityOnCreateFingerprint",
    returnType = "V",
    parameters = listOf("Landroid/os/Bundle;"),
    customFingerprint = { method, classDef ->
        classDef.endsWith("/WebViewHostActivity;") && method.name == "onCreate"
    }
)