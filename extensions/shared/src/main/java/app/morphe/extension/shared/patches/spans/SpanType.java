/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.shared.patches.spans;

import androidx.annotation.NonNull;

public enum SpanType {
    ABSOLUTE_SIZE("AbsoluteSizeSpan"),
    CLICKABLE("ClickableSpan"),
    CUSTOM_CHARACTER_STYLE("CustomCharacterStyle"),
    FOREGROUND_COLOR("ForegroundColorSpan"),
    IMAGE("ImageSpan"),
    LINE_HEIGHT("LineHeightSpan"),
    TYPEFACE("TypefaceSpan"),
    UNKNOWN("Unknown");

    @NonNull
    public final String type;

    SpanType(@NonNull String type) {
        this.type = type;
    }
}
