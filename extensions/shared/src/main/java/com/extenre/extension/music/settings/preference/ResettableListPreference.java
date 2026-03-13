/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.music.settings.preference;

import static com.extenre.extension.music.utils.ExtendedUtils.getDialogBuilder;
import static com.extenre.extension.shared.utils.ResourceUtils.getStringArray;
import static com.extenre.extension.shared.utils.StringRef.str;

import android.app.Activity;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Locale;

import com.extenre.extension.shared.settings.EnumSetting;
import com.extenre.extension.shared.settings.IntegerSetting;
import com.extenre.extension.shared.settings.LongSetting;
import com.extenre.extension.shared.settings.Setting;
import com.extenre.extension.shared.settings.StringSetting;
import com.extenre.extension.shared.utils.Logger;

public class ResettableListPreference {
    private static int mClickedDialogEntryIndex;

    public static void showDialog(Activity mActivity, @NonNull Setting<?> setting, int defaultIndex) {
        try {
            final String settingsKey = setting.key;

            final String entryKey = settingsKey + "_entries";
            final String entryValueKey = settingsKey + "_entry_values";
            final String[] mEntries = getStringArray(entryKey);
            final String[] mEntryValues = getStringArray(entryValueKey);

            final int findIndex = ArrayUtils.indexOf(mEntryValues, setting.get().toString());
            mClickedDialogEntryIndex = findIndex >= 0 ? findIndex : defaultIndex;

            getDialogBuilder(mActivity)
                    .setTitle(str(settingsKey + "_title"))
                    .setSingleChoiceItems(mEntries, mClickedDialogEntryIndex,
                            (dialog, id) -> mClickedDialogEntryIndex = id)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setNeutralButton(str("extenre_settings_reset"), (dialog, which) -> {
                        setting.resetToDefault();
                        ExtenRePreferenceFragment.showRebootDialog();
                    })
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        String value = mEntryValues[mClickedDialogEntryIndex];
                        if (setting instanceof StringSetting stringSetting) {
                            stringSetting.save(value);
                        } else if (setting instanceof IntegerSetting integerSetting) {
                            integerSetting.save(Integer.parseInt(value));
                        } else if (setting instanceof LongSetting longSetting) {
                            longSetting.save(Long.parseLong(value));
                        }
                        ExtenRePreferenceFragment.showRebootDialog();
                    })
                    .show();
        } catch (Exception ex) {
            Logger.printException(() -> "showDialog failure", ex);
        }
    }

    public static void showDialog(Activity mActivity, @NonNull EnumSetting<?> setting, int defaultIndex) {
        try {
            final String settingsKey = setting.key;

            final String entryKey = settingsKey + "_entries";
            final String entryValueKey = settingsKey + "_entry_values";
            final String[] mEntries = getStringArray(entryKey);
            final String[] mEntryValues = getStringArray(entryValueKey);

            final int findIndex = ArrayUtils.indexOf(mEntryValues, setting.get().toString().toUpperCase(Locale.ENGLISH));
            mClickedDialogEntryIndex = findIndex >= 0 ? findIndex : defaultIndex;

            getDialogBuilder(mActivity)
                    .setTitle(str(settingsKey + "_title"))
                    .setSingleChoiceItems(mEntries, mClickedDialogEntryIndex,
                            (dialog, id) -> mClickedDialogEntryIndex = id)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setNeutralButton(str("extenre_settings_reset"), (dialog, which) -> {
                        setting.resetToDefault();
                        ExtenRePreferenceFragment.showRebootDialog();
                    })
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        setting.saveValueFromString(mEntryValues[mClickedDialogEntryIndex]);
                        ExtenRePreferenceFragment.showRebootDialog();
                    })
                    .show();
        } catch (Exception ex) {
            Logger.printException(() -> "showDialog failure", ex);
        }
    }
}
