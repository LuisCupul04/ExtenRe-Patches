/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.shared.audiotracks

import app.morphe.util.containsLiteralInstruction
import app.morphe.util.fingerprint.legacyFingerprint
import app.morphe.util.or
import com.android.tools.smali.dexlib2.AccessFlags

internal const val AUDIO_STREAM_IGNORE_DEFAULT_FEATURE_FLAG = 45666189L

internal val selectAudioStreamFingerprint = legacyFingerprint(
    name = "selectAudioStreamFingerprint",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.STATIC,
    returnType = "L",
    customFingerprint = { method, _ ->
        method.parameters.size > 2 // Method has a large number of parameters and may change.
                && method.containsLiteralInstruction(AUDIO_STREAM_IGNORE_DEFAULT_FEATURE_FLAG)
    }
)
