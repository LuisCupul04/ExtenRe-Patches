/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.shared

import com.extenre.extension.shared.utils.Event
import com.extenre.extension.shared.utils.Logger

/**
 * PlayerControls visibility state.
 */
enum class PlayerControlsVisibility {
    PLAYER_CONTROLS_VISIBILITY_UNKNOWN,
    PLAYER_CONTROLS_VISIBILITY_WILL_HIDE,
    PLAYER_CONTROLS_VISIBILITY_HIDDEN,
    PLAYER_CONTROLS_VISIBILITY_WILL_SHOW,
    PLAYER_CONTROLS_VISIBILITY_SHOWN;

    companion object {

        private val nameToPlayerControlsVisibility =
            PlayerControlsVisibility.entries.associateBy { it.name }

        @JvmStatic
        fun setFromString(enumName: String) {
            val newType = nameToPlayerControlsVisibility[enumName]
            if (newType == null) {
                Logger.printException { "Unknown PlayerControlsVisibility encountered: $enumName" }
            } else {
                current = newType
            }
        }

        /**
         * Depending on which hook this is called from,
         * this value may not be up to date with the actual playback state.
         */
        @JvmStatic
        var current
            get() = currentPlayerControlsVisibility
            private set(type) {
                if (currentPlayerControlsVisibility != type) {
                    Logger.printDebug { "Changed to: $type" }

                    currentPlayerControlsVisibility = type
                    onChange(type)
                }
            }

        @Volatile // Read/write from different threads.
        private var currentPlayerControlsVisibility = PLAYER_CONTROLS_VISIBILITY_UNKNOWN

        @JvmStatic
        val onChange = Event<PlayerControlsVisibility>()
    }
}