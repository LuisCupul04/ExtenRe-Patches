/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.youtube.swipecontrols.misc

fun Float.clamp(min: Float, max: Float): Float {
    if (this < min) return min
    if (this > max) return max
    return this
}

fun Int.clamp(min: Int, max: Int): Int {
    if (this < min) return min
    if (this > max) return max
    return this
}
