/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.shared.spans

import app.morphe.util.fingerprint.legacyFingerprint

internal val customCharacterStyleFingerprint = legacyFingerprint(
    name = "customCharacterStyleFingerprint",
    returnType = "Landroid/graphics/Path;",
    parameters = listOf("Landroid/text/Layout;"),
)

