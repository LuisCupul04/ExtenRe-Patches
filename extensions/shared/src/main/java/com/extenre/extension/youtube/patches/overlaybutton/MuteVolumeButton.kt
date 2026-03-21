/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.overlaybutton

import android.content.Context
import android.media.AudioManager
import android.view.View
import com.extenre.extension.shared.utils.Logger
import com.extenre.extension.youtube.settings.Settings
import com.extenre.extension.youtube.shared.PlayerControlButton
import com.extenre.extension.youtube.shared.RootView.isAdProgressTextVisible

@Suppress("DEPRECATION", "unused")
object MuteVolumeButton {
    private var instance: PlayerControlButton? = null
    private var audioManager: AudioManager? = null
    private var stream: Int = AudioManager.STREAM_MUSIC

    /**
     * Injection point.
     */
    @JvmStatic
    fun initializeButton(controlsView: View) {
        try {
            instance = PlayerControlButton(
                controlsViewGroup = controlsView,
                imageViewButtonId = "extenre_mute_volume_button",
                buttonVisibility = { isButtonEnabled() },
                onClickListener = { view: View -> onClick(view) },
            )
            audioManager =
                controlsView.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        } catch (ex: Exception) {
            Logger.printException({ "initializeButton failure" }, ex)
        }
    }

    /**
     * injection point
     */
    @JvmStatic
    fun setVisibilityNegatedImmediate() {
        instance?.setVisibilityNegatedImmediate()
    }

    /**
     * injection point
     */
    @JvmStatic
    fun setVisibilityImmediate(visible: Boolean) {
        instance?.setActivated()
        instance?.setVisibilityImmediate(visible)
    }

    /**
     * injection point
     */
    @JvmStatic
    fun setVisibility(visible: Boolean, animated: Boolean) {
        instance?.setActivated()
        instance?.setVisibility(visible, animated)
    }

    private fun isButtonEnabled(): Boolean {
        return Settings.OVERLAY_BUTTON_MUTE_VOLUME.get()
                && !isAdProgressTextVisible()
    }

    private fun onClick(view: View) {
        if (instance != null && audioManager != null) {
            val unMuted = !audioManager!!.isStreamMute(stream)
            audioManager?.setStreamMute(stream, unMuted)
            instance?.imageView()?.isActivated = unMuted
        }
    }

    private fun PlayerControlButton.setActivated() {
        if (audioManager != null) {
            val muted = audioManager!!.isStreamMute(stream)
            imageView()?.isActivated = muted
        }
    }
}