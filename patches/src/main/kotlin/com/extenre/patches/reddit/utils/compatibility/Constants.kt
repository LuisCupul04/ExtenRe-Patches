/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.reddit.utils.compatibility

import com.extenre.patcher.patch.PackageName
import com.extenre.patcher.patch.VersionName

internal object Constants {
    internal const val REDDIT_PACKAGE_NAME = "com.reddit.frontpage"

    val COMPATIBLE_PACKAGE: Pair<PackageName, Set<VersionName>?> = Pair(
        REDDIT_PACKAGE_NAME,
        setOf(
            "2024.17.0", // This is the last version that can be patched without anti-split.
            "2025.05.1", // This was the latest version supported by the previous RVX patch.
            "2025.12.1", // This is the latest version supported by the RVX patch.
            "2026.05.1",
        )
    )
}