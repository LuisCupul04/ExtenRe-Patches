/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.utils.auth

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patches.shared.extension.Constants.EXTENSION_PATH
import app.morphe.patches.youtube.utils.extension.sharedExtensionPatch
import app.morphe.patches.youtube.utils.request.buildRequestPatch
import app.morphe.patches.youtube.utils.request.hookBuildRequest
import app.morphe.patches.youtube.utils.request.hookInitPlaybackBuildRequest
import app.morphe.util.fingerprint.mutableMethodOrThrow

private const val EXTENSION_AUTH_UTILS_CLASS_DESCRIPTOR =
    "$EXTENSION_PATH/innertube/utils/AuthUtils;"

val authHookPatch = bytecodePatch(
    name = "auth-Hook-Patch",
    description = "authHookPatch"
) {
    dependsOn(
        sharedExtensionPatch,
        buildRequestPatch,
    )

    execute {
        // Get incognito status and data sync id.
        accountIdentityFingerprint.mutableMethodOrThrow().addInstruction(
            1,
            "invoke-static {p3, p4}, $EXTENSION_AUTH_UTILS_CLASS_DESCRIPTOR->setAccountIdentity(Ljava/lang/String;Z)V"
        )

        // Get the header to use the auth token.
        hookBuildRequest("$EXTENSION_AUTH_UTILS_CLASS_DESCRIPTOR->setRequestHeaders(Ljava/lang/String;Ljava/util/Map;)V")
        hookInitPlaybackBuildRequest("$EXTENSION_AUTH_UTILS_CLASS_DESCRIPTOR->setRequestHeaders(Ljava/lang/String;Ljava/util/Map;)V")
    }
}