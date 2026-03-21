/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.youtube.settings.preference;

import static com.extenre.extension.shared.utils.StringRef.str;

import android.app.Dialog;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.LinearLayout;

import com.extenre.extension.shared.innertube.client.YouTubeClient;
import com.extenre.extension.shared.patches.auth.YouTubeVRAuthPatch;
import com.extenre.extension.shared.ui.CustomDialog;
import com.extenre.extension.youtube.settings.Settings;

@SuppressWarnings({"FieldCanBeLocal", "deprecation", "unused"})
public class SpoofStreamingDataSignInPreference extends Preference implements Preference.OnPreferenceClickListener {

    private void init() {
        setSelectable(true);
        setOnPreferenceClickListener(this);
        setEnabled(Settings.SPOOF_STREAMING_DATA_DEFAULT_CLIENT.get() == YouTubeClient.ClientType.ANDROID_VR);
    }

    public SpoofStreamingDataSignInPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public SpoofStreamingDataSignInPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SpoofStreamingDataSignInPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpoofStreamingDataSignInPreference(Context context) {
        super(context);
        init();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Context context = getContext();
        Pair<Dialog, LinearLayout> dialogPair;
        String dialogTitle = str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_title");
        String dialogMessage = str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_message");
        String resetButtonText = str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_reset_text");
        if (YouTubeVRAuthPatch.isDeviceCodeAvailable()) {
            dialogPair = CustomDialog.create(
                    context,
                    // Title.
                    dialogTitle,
                    // Message.
                    dialogMessage,
                    // No EditText.
                    null,
                    // OK button text.
                    str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_get_authorization_token_text"),
                    // OK button action.
                    () -> {
                        YouTubeVRAuthPatch.setRefreshToken();
                        YouTubeVRAuthPatch.setAccessToken(context);
                    },
                    // Cancel button action.
                    null,
                    // Neutral button text.
                    resetButtonText,
                    // Neutral button action.
                    YouTubeVRAuthPatch::clearAll,
                    // Dismiss dialog when onNeutralClick.
                    true
            );
        } else {
            dialogPair = CustomDialog.create(
                    context,
                    // Title.
                    dialogTitle,
                    // Message.
                    dialogMessage,
                    // No EditText.
                    null,
                    // OK button text.
                    str("extenre_spoof_streaming_data_sign_in_android_vr_dialog_get_activation_code_text"),
                    // OK button action.
                    () -> YouTubeVRAuthPatch.setActivationCode(context),
                    // Cancel button action.
                    null,
                    // Neutral button text.
                    resetButtonText,
                    // Neutral button action.
                    YouTubeVRAuthPatch::clearAll,
                    // Dismiss dialog when onNeutralClick.
                    true
            );
        }
        dialogPair.first.show();
        return true;
    }
}