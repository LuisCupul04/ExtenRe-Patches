/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.music.settings.preference;

import static com.extenre.extension.music.utils.ExtendedUtils.getDialogBuilder;
import static com.extenre.extension.music.utils.ExtendedUtils.getLayoutParams;
import static com.extenre.extension.shared.utils.StringRef.str;

import android.app.Activity;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;

import com.extenre.extension.shared.settings.Setting;
import com.extenre.extension.shared.utils.Logger;

public class ResettableEditTextPreference {

    public static void showDialog(Activity mActivity, @NonNull Setting<String> setting) {
        try {
            final EditText textView = new EditText(mActivity);
            textView.setText(setting.get());

            TextInputLayout textInputLayout = new TextInputLayout(mActivity);
            textInputLayout.setLayoutParams(getLayoutParams());
            textInputLayout.addView(textView);

            FrameLayout container = new FrameLayout(mActivity);
            container.addView(textInputLayout);

            getDialogBuilder(mActivity)
                    .setTitle(str(setting.key + "_title"))
                    .setView(container)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setNeutralButton(str("extenre_settings_reset"), (dialog, which) -> {
                        setting.resetToDefault();
                        ExtenRePreferenceFragment.showRebootDialog();
                    })
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        setting.save(textView.getText().toString().trim());
                        ExtenRePreferenceFragment.showRebootDialog();
                    })
                    .show();
        } catch (Exception ex) {
            Logger.printException(() -> "showDialog failure", ex);
        }
    }

}
