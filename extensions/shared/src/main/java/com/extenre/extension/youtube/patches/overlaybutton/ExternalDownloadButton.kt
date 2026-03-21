/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.overlaybutton

import android.view.View
import com.extenre.extension.shared.utils.Logger
import com.extenre.extension.youtube.patches.utils.PlaylistPatch
import com.extenre.extension.youtube.settings.Settings
import com.extenre.extension.youtube.shared.PlayerControlButton
import com.extenre.extension.youtube.shared.RootView.isAdProgressTextVisible
import com.extenre.extension.youtube.shared.VideoInformation
import com.extenre.extension.youtube.utils.VideoUtils

@Suppress("unused")
object ExternalDownloadButton {
    private var instance: PlayerControlButton? = null

    /**
     * Injection point.
     */
    @JvmStatic
    fun initializeButton(controlsView: View) {
        try {
            instance = PlayerControlButton(
                controlsViewGroup = controlsView,
                imageViewButtonId = "extenre_external_download_button",
                buttonVisibility = { isButtonEnabled() },
                onClickListener = { view: View -> onClick(view) },
                onLongClickListener = { view: View ->
                    onLongClick(view)
                    true
                }
            )
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
        instance?.setVisibilityImmediate(visible)
    }

    /**
     * injection point
     */
    @JvmStatic
    fun setVisibility(visible: Boolean, animated: Boolean) {
        instance?.setVisibility(visible, animated)
    }

    private fun isButtonEnabled(): Boolean {
        return Settings.OVERLAY_BUTTON_EXTERNAL_DOWNLOADER.get()
                && !isAdProgressTextVisible()
    }

    private fun onClick(view: View) {
        if (Settings.OVERLAY_BUTTON_EXTERNAL_DOWNLOADER_QUEUE_MANAGER.get()) {
            PlaylistPatch.prepareDialogBuilder(VideoInformation.getVideoId())
        } else {
            VideoUtils.launchVideoExternalDownloader()
        }
    }

    private fun onLongClick(view: View) {
        VideoUtils.launchVideoExternalDownloader()
    }

}