/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.youtube.swipecontrols.misc

/**
 * a simple rectangle class
 */
data class Rectangle(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
) {
    val left = x
    val right = x + width
    val top = y
    val bottom = y + height
}

/**
 * is the point within this rectangle?
 */
operator fun Rectangle.contains(p: Point): Boolean =
    p.x in left..right && p.y in top..bottom
