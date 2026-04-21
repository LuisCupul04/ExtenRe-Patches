/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package app.morphe.extension.youtube.patches.utils;

import com.airbnb.lottie.LottieAnimationView;

import app.morphe.extension.shared.utils.Logger;

public class LottieAnimationViewPatch {

    public static void setLottieAnimationRawResources(LottieAnimationView lottieAnimationView, int rawRes) {
        if (lottieAnimationView == null) {
            Logger.printDebug(() -> "View is null");
            return;
        }
        if (rawRes == 0) {
            Logger.printDebug(() -> "Resource is not found");
            return;
        }
        setAnimation(lottieAnimationView, rawRes);
    }

    @SuppressWarnings("unused")
    private static void setAnimation(LottieAnimationView lottieAnimationView, int rawRes) {
        // Rest of the implementation added by patch.
    }
}
