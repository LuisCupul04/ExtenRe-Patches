/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.youtube.patches.components;

import app.morphe.extension.shared.patches.components.Filter;
import app.morphe.extension.shared.patches.components.StringFilterGroup;
import app.morphe.extension.youtube.patches.video.AdvancedVideoQualityMenuPatch;
import app.morphe.extension.youtube.settings.Settings;

/**
 * LithoFilter for {@link AdvancedVideoQualityMenuPatch}.
 */
public final class VideoQualityMenuFilter extends Filter {
    // Must be volatile or synchronized, as litho filtering runs off main thread and this field is then access from the main thread.
    public static volatile boolean isVideoQualityMenuVisible;

    public VideoQualityMenuFilter() {
        addPathCallbacks(
                new StringFilterGroup(
                        Settings.ADVANCED_VIDEO_QUALITY_MENU,
                        "quick_quality_sheet_content."
                )
        );
    }

    @Override
    public boolean isFiltered(String path, String identifier, String allValue, byte[] buffer,
                              StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        isVideoQualityMenuVisible = true;

        return false;
    }
}
