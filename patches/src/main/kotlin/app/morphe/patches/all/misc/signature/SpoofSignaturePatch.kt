/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.all.misc.signature

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.stringOption

@Suppress("unused")
val spoofSignaturePatch = bytecodePatch(
    name = "Spoof signature",
    description = "Spoofs the signature of the app.",
    use = false,
) {
    val packageName by stringOption(
        key = "packageName",
        default = "",
        title = "Package name",
        description = "Package name.",
    )

    val certificateData by stringOption(
        key = "certificateData",
        default = "",
        title = "Certificate data",
        description = "Base64-encoded string of the certificate.",
    )

    dependsOn(
        baseSpoofSignaturePatch {
            AppInfo(
                packageName,
                certificateData,
            )
        },
    )
}
