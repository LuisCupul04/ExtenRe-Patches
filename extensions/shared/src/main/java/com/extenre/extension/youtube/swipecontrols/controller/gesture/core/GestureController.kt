/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.swipecontrols.controller.gesture.core

import android.view.MotionEvent

/**
 * describes a class that accepts motion events and detects gestures
 */
interface GestureController {
    /**
     * accept a touch event and try to detect the desired gestures using it
     *
     * @param motionEvent the motion event that was submitted
     * @return was a gesture detected?
     */
    fun submitTouchEvent(motionEvent: MotionEvent): Boolean
}
