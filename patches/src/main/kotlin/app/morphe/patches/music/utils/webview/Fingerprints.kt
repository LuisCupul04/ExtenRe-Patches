/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.music.utils.webview

import app.morphe.util.fingerprint.legacyFingerprint

internal val carAppPermissionActivityOnCreateFingerprint = legacyFingerprint(
    name = "carAppPermissionActivityOnCreateFingerprint",
    returnType = "V",
    parameters = listOf("Landroid/os/Bundle;"),
    customFingerprint = { method, classDef ->
        classDef.type == "Landroidx/car/app/CarAppPermissionActivity;"
                && method.name == "onCreate"
    }
)
