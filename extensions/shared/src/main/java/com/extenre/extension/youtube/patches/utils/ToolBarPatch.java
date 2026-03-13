/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.patches.utils;

import android.view.View;
import android.widget.ImageView;

import com.extenre.extension.shared.utils.Logger;

@SuppressWarnings("unused")
public class ToolBarPatch {

    public static void hookToolBar(Enum<?> buttonEnum, ImageView imageView) {
        final String enumString = buttonEnum.name();
        if (enumString.isEmpty() ||
                imageView == null ||
                !(imageView.getParent() instanceof View view)) {
            return;
        }

        Logger.printDebug(() -> "enumString: " + enumString);

        hookToolBar(enumString, view);
    }

    private static void hookToolBar(String enumString, View parentView) {
    }
}


