/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.utils.dismiss

import app.morphe.util.fingerprint.legacyFingerprint
import app.morphe.util.or
import com.android.tools.smali.dexlib2.AccessFlags

internal const val DISMISS_PLAYER_LITERAL = 34699L

internal val dismissPlayerOnClickListenerFingerprint = legacyFingerprint(
    name = "dismissPlayerOnClickListenerFingerprint",
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    literals = listOf(DISMISS_PLAYER_LITERAL),
    customFingerprint = { method, _ ->
        method.name == "onClick"
    }
)
