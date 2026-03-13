/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.shared.returnyoutubeusername.requests;

import static com.extenre.extension.shared.requests.Route.Method.GET;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.extenre.extension.shared.requests.Requester;
import com.extenre.extension.shared.requests.Route;

public class ChannelRoutes {
    public static final String YOUTUBEI_V3_GAPIS_URL = "https://www.googleapis.com/youtube/v3/";

    public static final Route GET_CHANNEL_DETAILS = new Route(GET, "channels?part=brandingSettings&maxResults=1&prettyPrint=false&forHandle={handle}&key={api_key}");

    public ChannelRoutes() {
    }

    public static HttpURLConnection getChannelConnectionFromRoute(Route route, String... params) throws IOException {
        return Requester.getConnectionFromRoute(YOUTUBEI_V3_GAPIS_URL, route, params);
    }
}