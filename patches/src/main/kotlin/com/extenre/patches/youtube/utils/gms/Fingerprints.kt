/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.patches.youtube.utils.gms

import com.extenre.patches.youtube.utils.resourceid.icOfflineNoContentUpsideDown
import com.extenre.patches.youtube.utils.resourceid.offlineNoContentBodyTextNotOfflineEligible
import com.extenre.util.fingerprint.legacyFingerprint
import com.extenre.util.or
import com.android.tools.smali.dexlib2.AccessFlags

internal val specificNetworkErrorViewControllerFingerprint = legacyFingerprint(
    name = "specificNetworkErrorViewControllerFingerprint",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    returnType = "V",
    parameters = emptyList(),
    literals = listOf(icOfflineNoContentUpsideDown, offlineNoContentBodyTextNotOfflineEligible),
)

// It's not clear if this second class is ever used and it may be dead code,
// but it the layout image / text is identical to the network error fingerprint above.
internal val loadingFrameLayoutControllerFingerprint = legacyFingerprint(
    name = "loadingFrameLayoutControllerFingerprint",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    returnType = "V",
    parameters = listOf("L"),
    literals = listOf(icOfflineNoContentUpsideDown, offlineNoContentBodyTextNotOfflineEligible),
)
