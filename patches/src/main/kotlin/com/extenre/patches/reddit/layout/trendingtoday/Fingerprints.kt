/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.reddit.layout.trendingtoday

import com.extenre.util.fingerprint.legacyFingerprint
import com.android.tools.smali.dexlib2.Opcode

internal val trendingTodayTitleFingerprint = legacyFingerprint(
    name = "trendingTodayTitleFingerprint",
    opcodes = listOf(Opcode.AND_INT_LIT8),
    strings = listOf("trending_today_title"),
    customFingerprint = { _, classDef ->
        classDef.type.startsWith("Lcom/reddit/typeahead/ui/zerostate/composables")
    },
)

internal val trendingTodayItemFingerprint = legacyFingerprint(
    name = "trendingTodayItemFingerprint",
    returnType = "V",
    strings = listOf("search_trending_item"),
    customFingerprint = { _, classDef ->
        classDef.type.startsWith("Lcom/reddit/typeahead/ui/zerostate/composables")
    },
)
