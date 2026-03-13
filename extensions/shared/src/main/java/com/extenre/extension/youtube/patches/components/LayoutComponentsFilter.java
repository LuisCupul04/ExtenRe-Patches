/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.components;

import com.extenre.extension.shared.patches.components.Filter;
import com.extenre.extension.shared.patches.components.StringFilterGroup;
import com.extenre.extension.youtube.settings.Settings;

@SuppressWarnings("unused")
public final class LayoutComponentsFilter extends Filter {
    private static final String ACCOUNT_HEADER_PATH = "account_header.";
    private static final String HANDLE_PATH = "|CellType|ContainerType|ContainerType|ContainerType|TextType|";

    public LayoutComponentsFilter() {
        addIdentifierCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_VISUAL_SPACER,
                        "cell_divider"
                )
        );

        addPathCallbacks(
                new StringFilterGroup(
                        Settings.HIDE_HANDLE,
                        ACCOUNT_HEADER_PATH
                )
        );
    }

    @Override
    public boolean isFiltered(String path, String identifier, String allValue, byte[] buffer,
                              StringFilterGroup matchedGroup, FilterContentType contentType, int contentIndex) {
        return contentType != FilterContentType.PATH || (contentIndex == 0 && path.contains(HANDLE_PATH));
    }
}
