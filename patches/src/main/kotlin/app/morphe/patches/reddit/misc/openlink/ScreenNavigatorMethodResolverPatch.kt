/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.reddit.misc.openlink

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.util.proxy.mutableTypes.MutableMethod
import app.morphe.util.fingerprint.mutableMethodOrThrow
import app.morphe.util.getWalkerMethod

lateinit var screenNavigatorMethod: MutableMethod

val screenNavigatorMethodResolverPatch = bytecodePatch(
    name = "screen-Navigator-Method-Resolver-Patch",
    description = "screenNavigatorMethodResolverPatch"
) {
    execute {
        screenNavigatorMethod =
                // ~ Reddit 2024.25.3
            screenNavigatorFingerprint.second.methodOrNull
                    // Reddit 2024.26.1 ~
                ?: with(customReportsFingerprint.mutableMethodOrThrow()) {
                    getWalkerMethod(indexOfScreenNavigatorInstruction(this))
                }
    }
}
