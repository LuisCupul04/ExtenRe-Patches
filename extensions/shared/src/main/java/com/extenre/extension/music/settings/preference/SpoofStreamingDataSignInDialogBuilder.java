/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.music.settings.preference;

import static com.extenre.extension.shared.utils.StringRef.str;

import android.app.Activity;
import android.app.AlertDialog;

import com.extenre.extension.shared.patches.auth.YouTubeAuthPatch;
import com.extenre.extension.shared.patches.auth.YouTubeVRAuthPatch;
import com.extenre.extension.shared.utils.IntentUtils;
import com.extenre.extension.shared.utils.Utils;

public class SpoofStreamingDataSignInDialogBuilder {

    public static void showNoSDKDialog(Activity mActivity) {
        AlertDialog.Builder builder = Utils.getDialogBuilder(mActivity);

        String dialogTitle =
                str("extenre_spoof_streaming_data_sign_in_android_no_sdk_dialog_title");
        String dialogMessage =
                str("extenre_spoof_streaming_data_sign_in_android_no_sdk_dialog_message");
        String resetButtonText =
                str("extenre_spoof_streaming_data_sign_in_android_no_sdk_dialog_reset_text");
        String okButtonText =
                str("extenre_spoof_streaming_data_sign_in_android_no_sdk_dialog_get_authorization_token_text");

        builder.setTitle(dialogTitle);
        builder.setMessage(dialogMessage);
        builder.setNeutralButton(resetButtonText, (dialog, id) -> YouTubeAuthPatch.clearAll());
        builder.setPositiveButton(okButtonText, (dialog, id) -> IntentUtils.launchWebViewActivity(
                mActivity,
                true,
                true,
                false,
                "https://accounts.google.com/EmbeddedSetup"
        ));
        builder.show();
    }

    public static void showVRDialog(Activity mActivity) {
        AlertDialog.Builder builder = Utils.getDialogBuilder(mActivity);

        String dialogTitle =
                str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_title");
        String dialogMessage =
                str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_message");
        String resetButtonText =
                str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_reset_text");

        builder.setTitle(dialogTitle);
        builder.setMessage(dialogMessage);
        builder.setNeutralButton(resetButtonText, (dialog, id) -> YouTubeVRAuthPatch.clearAll());

        if (YouTubeVRAuthPatch.isDeviceCodeAvailable()) {
            String okButtonText =
                    str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_get_authorization_token_text");
            builder.setPositiveButton(okButtonText, (dialog, id) -> {
                YouTubeVRAuthPatch.setRefreshToken();
                YouTubeVRAuthPatch.setAccessToken(mActivity);
            });
        } else {
            String okButtonText =
                    str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_get_activation_code_text");
            builder.setPositiveButton(okButtonText, (dialog, id) -> {
                YouTubeVRAuthPatch.setActivationCode(mActivity);
            });
        }
        builder.show();
    }
}