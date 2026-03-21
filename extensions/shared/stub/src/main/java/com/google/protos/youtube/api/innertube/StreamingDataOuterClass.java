/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.google.protos.youtube.api.innertube;

import java.util.Collections;
import java.util.List;

// YouTube 19.47.53
public class StreamingDataOuterClass {
    public interface StreamingData {
        // UNKNOWN
        int c = 0;

        // expiresInSeconds
        long d = 0;

        // formats
        List<?> e = Collections.emptyList();

        // adaptiveFormats
        List<?> f = Collections.emptyList();

        // metadataFormats
        List<?> g = Collections.emptyList();

        // dashManifestUrl
        String h = null;

        // hlsManifestUrl
        String i = null;

        // UNKNOWN
        String j = null;

        // drmParams
        String k = null;

        // serverAbrStreamingUrl
        String l = null;

        // licenseInfos? or initialAuthorizedDrmTrackTypes?
        List<?> m = Collections.emptyList();
    }
}