/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.reddit.utils.extension.hooks

import com.extenre.patches.shared.extension.extensionHook

internal val applicationInitHook = extensionHook {
    custom { method, _ ->
        method.definingClass.endsWith("/FrontpageApplication;") &&
                method.name == "onCreate"
    }
}
