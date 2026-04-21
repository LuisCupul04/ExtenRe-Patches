/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.general.signintotvpopup

import app.morphe.patches.youtube.utils.resourceid.mdxSeamlessTVSignInDrawerFragmentTitle
import app.morphe.util.fingerprint.legacyFingerprint
import app.morphe.util.or
import com.android.tools.smali.dexlib2.AccessFlags

internal val signInToTvPopupFingerprint = legacyFingerprint(
    name = "signInToTvPopupFingerprint",
    returnType = "Z",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    literals = listOf(mdxSeamlessTVSignInDrawerFragmentTitle),
    customFingerprint = { method, _ ->
        method.parameterTypes.firstOrNull() == "Ljava/lang/String;"
    }
)
