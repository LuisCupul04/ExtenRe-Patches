/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.general.updates

import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags

internal val cronetHeaderFingerprint = legacyFingerprint(
    name = "cronetHeaderFingerprint",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("Ljava/lang/String;", "Ljava/lang/String;"),
    strings = listOf("Accept-Encoding"),
    // In YouTube 19.16.39 or earlier, there are two methods with almost the same structure.
    // Check the fields of the class to identify them correctly.
    customFingerprint = { _, classDef ->
        classDef.fields.find { it.type == "J" } != null
    }
)
