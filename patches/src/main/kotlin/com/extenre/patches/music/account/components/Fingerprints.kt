/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.music.account.components

import com.extenre.patches.music.utils.resourceid.accountSwitcherAccessibility
import com.extenre.patches.music.utils.resourceid.channelHandle
import com.extenre.patches.music.utils.resourceid.menuEntry
import com.extenre.patches.music.utils.resourceid.namesInactiveAccountThumbnailSize
import com.extenre.patches.music.utils.resourceid.tosFooter
import com.extenre.util.fingerprint.legacyFingerprint

internal val accountSwitcherAccessibilityLabelFingerprint = legacyFingerprint(
    name = "accountSwitcherAccessibilityLabelFingerprint",
    returnType = "V",
    parameters = listOf("L", "Ljava/lang/Object;"),
    literals = listOf(accountSwitcherAccessibility)
)

internal val channelHandleFingerprint = legacyFingerprint(
    name = "channelHandleFingerprint",
    returnType = "V",
    literals = listOf(channelHandle),
)

internal val menuEntryFingerprint = legacyFingerprint(
    name = "menuEntryFingerprint",
    returnType = "V",
    literals = listOf(menuEntry)
)

internal val namesInactiveAccountThumbnailSizeFingerprint = legacyFingerprint(
    name = "namesInactiveAccountThumbnailSizeFingerprint",
    returnType = "V",
    parameters = listOf("L", "Ljava/lang/Object;"),
    literals = listOf(namesInactiveAccountThumbnailSize)
)

internal val termsOfServiceFingerprint = legacyFingerprint(
    name = "termsOfServiceFingerprint",
    returnType = "Landroid/view/View;",
    literals = listOf(tosFooter)
)
