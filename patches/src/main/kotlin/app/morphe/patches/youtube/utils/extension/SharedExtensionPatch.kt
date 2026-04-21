/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.patches.youtube.utils.extension

import app.morphe.patches.shared.extension.sharedExtensionPatch
import app.morphe.patches.youtube.utils.extension.hooks.applicationInitHook

// TODO: Move this to a "Hook.kt" file. Same for other extension hook patches.
val sharedExtensionPatch = sharedExtensionPatch(
    applicationInitHook,
)
