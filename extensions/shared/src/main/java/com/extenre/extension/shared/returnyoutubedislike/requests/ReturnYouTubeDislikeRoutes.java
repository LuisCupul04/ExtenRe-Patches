/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.shared.returnyoutubedislike.requests;

import static com.extenre.extension.shared.requests.Route.Method.GET;
import static com.extenre.extension.shared.requests.Route.Method.POST;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.extenre.extension.shared.requests.Requester;
import com.extenre.extension.shared.requests.Route;

public class ReturnYouTubeDislikeRoutes {
    public static final String RYD_API_URL = "https://returnyoutubedislikeapi.com/";

    public static final Route SEND_VOTE = new Route(POST, "interact/vote");
    public static final Route CONFIRM_VOTE = new Route(POST, "interact/confirmVote");
    public static final Route GET_DISLIKES = new Route(GET, "votes?videoId={video_id}");
    public static final Route GET_REGISTRATION = new Route(GET, "puzzle/registration?userId={user_id}");
    public static final Route CONFIRM_REGISTRATION = new Route(POST, "puzzle/registration?userId={user_id}");

    public ReturnYouTubeDislikeRoutes() {
    }

    public static HttpURLConnection getRYDConnectionFromRoute(Route route, String... params) throws IOException {
        return Requester.getConnectionFromRoute(RYD_API_URL, route, params);
    }

}