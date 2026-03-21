/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.music.patches.misc;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.extenre.extension.music.patches.components.ShareSheetMenuFilter;
import com.extenre.extension.music.settings.Settings;
import com.extenre.extension.shared.utils.Logger;
import com.extenre.extension.shared.utils.Utils;

@SuppressWarnings("unused")
public class ShareSheetPatch {
    /**
     * Injection point.
     */
    public static void onShareSheetMenuCreate(final RecyclerView recyclerView) {
        if (!Settings.CHANGE_SHARE_SHEET.get())
            return;

        recyclerView.getViewTreeObserver().addOnDrawListener(() -> {
            try {
                if (!ShareSheetMenuFilter.isShareSheetMenuVisible)
                    return;
                if (!(recyclerView.getChildAt(0) instanceof ViewGroup shareContainer)) {
                    return;
                }
                if (!(shareContainer.getChildAt(shareContainer.getChildCount() - 1) instanceof ViewGroup shareWithOtherAppsViewGroup)) {
                    return;
                }
                if (!(shareWithOtherAppsViewGroup.getChildAt(shareWithOtherAppsViewGroup.getChildCount() - 1) instanceof ViewGroup shareWithOtherAppsView)) {
                    return;
                }
                ShareSheetMenuFilter.isShareSheetMenuVisible = false;

                recyclerView.setVisibility(View.GONE);
                Utils.clickView(shareWithOtherAppsView);
            } catch (Exception ex) {
                Logger.printException(() -> "onShareSheetMenuCreate failure", ex);
            }
        });
    }

}
