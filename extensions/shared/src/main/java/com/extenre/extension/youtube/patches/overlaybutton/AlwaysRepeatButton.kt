/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.overlaybutton

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.toColorInt
import com.extenre.extension.shared.utils.Logger
import com.extenre.extension.shared.utils.StringRef.str
import com.extenre.extension.shared.utils.Utils.showToastShort
import com.extenre.extension.youtube.settings.Settings
import com.extenre.extension.youtube.shared.PlayerControlButton
import com.extenre.extension.youtube.shared.RootView.isAdProgressTextVisible

@Suppress("unused")
object AlwaysRepeatButton {
    private val alwaysRepeat = Settings.ALWAYS_REPEAT
    private val alwaysRepeatPause = Settings.ALWAYS_REPEAT_PAUSE
    private val cf: ColorFilter =
        PorterDuffColorFilter("#fffffc79".toColorInt(), PorterDuff.Mode.SRC_ATOP)
    private var instance: PlayerControlButton? = null

    /**
     * Injection point.
     */
    @JvmStatic
    fun initializeButton(controlsView: View) {
        try {
            instance = PlayerControlButton(
                controlsViewGroup = controlsView,
                imageViewButtonId = "extenre_always_repeat_button",
                buttonVisibility = { isButtonEnabled() },
                onClickListener = { view: View -> onClick(view) },
                onLongClickListener = { view: View ->
                    onLongClick(view)
                    true
                }
            )
            instance?.changeSelected(selected = alwaysRepeat.get())
            instance?.setColorFilter(selected = alwaysRepeatPause.get())
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
        return Settings.OVERLAY_BUTTON_ALWAYS_REPEAT.get()
                && !isAdProgressTextVisible()
    }

    private fun onClick(view: View) {
        instance?.changeSelected(!view.isSelected)
    }

    private fun onLongClick(view: View) {
        instance?.changeColorFilter()
    }

    private fun PlayerControlButton.changeColorFilter() {
        val imageView: ImageView? = imageView()
        if (imageView == null) return

        imageView.isSelected = true
        alwaysRepeat.save(true)

        val newValue: Boolean = !alwaysRepeatPause.get()
        alwaysRepeatPause.save(newValue)
        setColorFilter(imageView, newValue)
    }

    private fun PlayerControlButton.changeSelected(selected: Boolean) {
        val imageView: ImageView? = imageView()
        if (imageView == null) return

        if (imageView.colorFilter === cf) {
            showToastShort(str("extenre_overlay_button_not_allowed_warning"))
            return
        }

        imageView.isSelected = selected
        alwaysRepeat.save(selected)
    }

    private fun PlayerControlButton.setColorFilter(
        imageView: ImageView? = imageView(),
        selected: Boolean
    ) {
        if (selected) imageView?.colorFilter = cf
        else imageView?.clearColorFilter()
    }

}