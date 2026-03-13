/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.music.sponsorblock.objects;

import static com.extenre.extension.shared.utils.StringRef.sf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import com.extenre.extension.shared.utils.StringRef;

public enum CategoryBehaviour {
    SKIP_AUTOMATICALLY("skip", 2, true, sf("extenre_sb_skip_automatically")),
    // ignored categories are not exported to json, and ignore is the default behavior when importing
    IGNORE("ignore", -1, false, sf("extenre_sb_skip_ignore"));

    /**
     * ReVanced specific value.
     */
    @NonNull
    public final String ExTenReKeyValue;
    /**
     * Desktop specific value.
     */
    public final int desktopKeyValue;
    /**
     * If the segment should skip automatically
     */
    public final boolean skipAutomatically;
    @NonNull
    public final StringRef description;

    CategoryBehaviour(String ExTenReKeyValue, int desktopKeyValue, boolean skipAutomatically, StringRef description) {
        this.ExTenReKeyValue = Objects.requireNonNull(ExTenReKeyValue);
        this.desktopKeyValue = desktopKeyValue;
        this.skipAutomatically = skipAutomatically;
        this.description = Objects.requireNonNull(description);
    }

    @Nullable
    public static CategoryBehaviour byExTenReKeyValue(@NonNull String keyValue) {
        for (CategoryBehaviour behaviour : values()) {
            if (behaviour.ExTenReKeyValue.equals(keyValue)) {
                return behaviour;
            }
        }
        return null;
    }
}
