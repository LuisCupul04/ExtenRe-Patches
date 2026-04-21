/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

@file:Suppress("SpellCheckingInspection")

package app.morphe.patches.shared.quic

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.extension.Constants.PATCHES_PATH
import app.morphe.util.fingerprint.mutableMethodOrThrow

private const val EXTENSION_CLASS_DESCRIPTOR =
    "$PATCHES_PATH/QUICProtocolPatch;"

fun baseQuicProtocolPatch() = bytecodePatch(
    name = "base-Quic-Protocol-Patch",
    description = "baseQuicProtocolPatch"
) {
    execute {
        arrayOf(
            cronetEngineBuilderFingerprint,
            experimentalCronetEngineBuilderFingerprint
        ).forEach {
            it.mutableMethodOrThrow().addInstructions(
                0, """
                    invoke-static {p1}, $EXTENSION_CLASS_DESCRIPTOR->disableQUICProtocol(Z)Z
                    move-result p1
                    """
            )
        }
    }
}
