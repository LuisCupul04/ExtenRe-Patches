/*
 * Copyright (C) 2022 ReVanced LLC
 * Copyright (C) 2022 inotia00
 * Copyright (C) 2026 LuisCupul04
 *
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.extenre.extension.music.settings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.Toolbar;

import com.extenre.extension.music.settings.preference.YouTubeMusicPreferenceFragment;
import com.extenre.extension.music.settings.search.YouTubeMusicSearchViewController;
import com.extenre.extension.shared.settings.BaseHostActivity;
import com.extenre.extension.shared.utils.BaseThemeUtils;
import com.extenre.extension.shared.utils.ResourceUtils;

public class YouTubeMusicHostActivity extends BaseHostActivity {
    private final int EXTENRE_SETTINGS_BACKGROUND_COLOR =
            ResourceUtils.getColor("yt_black_pure");

    @SuppressLint("StaticFieldLeak")
    public static YouTubeMusicSearchViewController searchViewController;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onBackPressed() {
        if (handleBackPress()) {
            super.onBackPressed();
        }
    }

    /**
     * Sets the fixed theme for the activity.
     */
    @Override
    protected void customizeActivityTheme(Activity activity) {
        // Override the default YouTube Music theme to increase start padding of list items.
        // Custom style located in resources/music/values/style.xml
        activity.setTheme(ResourceUtils.getStyleIdentifier("Theme.ExtenRe.YouTubeMusic.Settings"));
        activity.getWindow().getDecorView().setBackgroundColor(EXTENRE_SETTINGS_BACKGROUND_COLOR);
    }

    /**
     * Returns the resource ID for the YouTube Music settings layout.
     */
    @Override
    protected int getContentViewResourceId() {
        return LAYOUT_EXTENRE_SETTINGS_WITH_TOOLBAR;
    }

    /**
     * Returns the toolbar background color based on dark/light mode.
     */
    @Override
    protected int getToolbarBackgroundColor() {
        return EXTENRE_SETTINGS_BACKGROUND_COLOR;
    }

    /**
     * Returns the navigation icon drawable for the toolbar.
     */
    @Override
    protected Drawable getNavigationIcon() {
        return BaseThemeUtils.getBackButtonDrawable();
    }

    /**
     * Returns the click listener that finishes the activity when the navigation icon is clicked.
     */
    @Override
    protected View.OnClickListener getNavigationClickListener(Activity activity) {
        return view -> {
            if (searchViewController != null && searchViewController.isSearchActive()) {
                searchViewController.closeSearch();
            } else {
                activity.finish();
            }
        };
    }

    /**
     * Adds search view components to the toolbar for {@link YouTubeMusicPreferenceFragment}.
     *
     * @param activity The activity hosting the toolbar.
     * @param toolbar  The configured toolbar.
     * @param fragment The PreferenceFragment associated with the activity.
     */
    @Override
    protected void onPostToolbarSetup(Activity activity, Toolbar toolbar, PreferenceFragment fragment) {
        if (fragment instanceof YouTubeMusicPreferenceFragment preferenceFragment) {
            searchViewController = YouTubeMusicSearchViewController.addSearchViewComponents(
                    activity, toolbar, preferenceFragment);
        }
    }

    /**
     * Creates a new {@link YouTubeMusicPreferenceFragment} for the activity.
     */
    @Override
    protected PreferenceFragment createPreferenceFragment() {
        return new YouTubeMusicPreferenceFragment();
    }

    /**
     * @return Whether pressing the back button should be allowed.
     */
    @Override
    protected boolean handleBackPress() {
        return YouTubeMusicSearchViewController.handleBackPress(searchViewController);
    }
}
