/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.swipecontrols.misc

import android.view.MotionEvent

/**
 * a simple 2D point class
 */
data class Point(
    val x: Int,
    val y: Int,
)

/**
 * convert the motion event coordinates to a point
 */
fun MotionEvent.toPoint(): Point =
    Point(x.toInt(), y.toInt())
