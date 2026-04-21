/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.shared.patches;

import app.morphe.extension.shared.settings.BaseSettings;

@SuppressWarnings("unused")
public class OpusCodecPatch {

    public static boolean enableOpusCodec() {
        return BaseSettings.ENABLE_OPUS_CODEC.get();
    }
}
