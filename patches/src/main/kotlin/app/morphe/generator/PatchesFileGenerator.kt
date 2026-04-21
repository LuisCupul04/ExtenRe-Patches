/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.generator

import app.morphe.patcher.patch.Patch

internal interface PatchesFileGenerator {
    fun generate(patches: Set<Patch<*>>)
}
