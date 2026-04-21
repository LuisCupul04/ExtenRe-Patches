/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.shared.viewgroup

import app.morphe.util.fingerprint.legacyFingerprint
import app.morphe.util.or
import com.android.tools.smali.dexlib2.AccessFlags

internal val viewGroupMarginFingerprint = legacyFingerprint(
    name = "viewGroupMarginFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.STATIC,
    parameters = listOf("Landroid/view/View;", "I", "I"),
)

internal val viewGroupMarginParentFingerprint = legacyFingerprint(
    name = "viewGroupMarginParentFingerprint",
    returnType = "Landroid/view/ViewGroup${'$'}LayoutParams;",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.STATIC,
    parameters = listOf("Ljava/lang/Class;", "Landroid/view/ViewGroup${'$'}LayoutParams;"),
    strings = listOf("SafeLayoutParams"),
)
