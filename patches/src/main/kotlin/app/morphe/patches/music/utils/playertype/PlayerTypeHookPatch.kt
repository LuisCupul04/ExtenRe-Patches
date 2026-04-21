/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.music.utils.playertype

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.music.utils.extension.Constants.UTILS_PATH
import app.morphe.util.fingerprint.mutableMethodOrThrow

private const val EXTENSION_CLASS_DESCRIPTOR =
    "$UTILS_PATH/PlayerTypeHookPatch;"

@Suppress("unused")
val playerTypeHookPatch = bytecodePatch(
    name = "player-type-hook-patch",
    description = "playerTypeHookPatch"
) {

    execute {

        playerTypeFingerprint.mutableMethodOrThrow().addInstruction(
            0,
            "invoke-static {p1}, $EXTENSION_CLASS_DESCRIPTOR->setPlayerType(Ljava/lang/Enum;)V"
        )

    }
}
