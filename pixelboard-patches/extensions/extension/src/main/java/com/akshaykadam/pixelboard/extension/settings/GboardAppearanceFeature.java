package com.akshaykadam.pixelboard.extension.settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public final class GboardAppearanceFeature implements GboardPatchesSettingsContract.Feature {

    public static final String PREF_NAME = "gboard_pro_appearance_prefs";
    public static final String KEY_SELECTED_ICON = "selected_app_icon_alias";
    public static final String KEY_UI_THEME = "selected_gboard_ui_theme";

    private final Context context;

    public GboardAppearanceFeature(Context context) {
        this.context = context;
    }

    @Override
    public String getEntryTitle() {
        return "Appearance";
    }

    @Override
    public String getEntrySummary() {
        return "App icons & styling";
    }

    @Override
    public boolean isAvailable(Context context) {
        return true;
    }

    @Override
    public void onVisible(GboardPatchesSettingsContract.FeatureHost host) {
    }

    @Override
    public GboardPatchesSettingsContract.Screen buildScreen(GboardPatchesSettingsContract.FeatureHost host) {
        Context ctx = host != null && host.getContext() != null ? host.getContext() : context;
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String currentIcon = prefs.getString(KEY_SELECTED_ICON, "launcher.Default");
        String currentTheme = prefs.getString(KEY_UI_THEME, "default");

        List<GboardPatchesSettingsContract.Section> sections = new ArrayList<>();

        // 1. App Launcher Icons Section
        List<GboardPatchesSettingsContract.Row> iconRows = new ArrayList<>();
        String[] iconAliases = {
                "launcher.Default",
                "launcher.Purple",
                "launcher.Slate",
                "launcher.ForestGreen",
                "launcher.NavyBlue"
        };
        String[] iconTitles = {
                "Classic Gboard Pro (Default)",
                "Expressive Purple",
                "Slate Charcoal",
                "Forest Emerald",
                "Midnight Navy"
        };
        String[] iconSummaries = {
                "Official modern Material You adaptive icon",
                "Vibrant violet-purple gradient with glow accent",
                "Minimalist graphite matte aesthetic",
                "Nature-inspired emerald green with deep sage",
                "Deep indigo and cobalt blue"
        };

        for (int i = 0; i < iconAliases.length; i++) {
            final String alias = iconAliases[i];
            boolean selected = alias.equals(currentIcon);
            iconRows.add(new GboardPatchesSettingsContract.RadioRow(
                    iconTitles[i],
                    iconSummaries[i],
                    true,
                    selected,
                    () -> {
                        applyAppIcon(ctx, alias);
                        if (host != null) {
                            host.submit(h -> h.refresh());
                        }
                    }
            ));
        }
        sections.add(new GboardPatchesSettingsContract.Section(
                "APP LAUNCHER ICONS",
                iconRows
        ));

        // 2. Gboard UI Themes Section with "beta" badge
        List<GboardPatchesSettingsContract.Row> themeRows = new ArrayList<>();
        String[] themeKeys = {"default", "rainbow", "botanical", "ocean"};
        String[] themeTitles = {
                "Default Material 3",
                "Expressive Rainbow",
                "Fresh Botanical",
                "Ocean Lavender"
        };
        String[] themeSummaries = {
                "Official adaptive dynamic palette based on system wallpaper",
                "Warm espresso and vivid sunset coral multi-tone vibe",
                "Deep forest moss and luminous sage botanical palette",
                "Midnight azure and ethereal twilight lavender multi-color"
        };

        for (int i = 0; i < themeKeys.length; i++) {
            final String key = themeKeys[i];
            boolean selected = key.equals(currentTheme);
            themeRows.add(new GboardPatchesSettingsContract.RadioRow(
                    themeTitles[i],
                    themeSummaries[i],
                    true,
                    selected,
                    () -> {
                        prefs.edit().putString(KEY_UI_THEME, key).apply();
                        Toast.makeText(ctx, "Applied " + key + " UI theme", Toast.LENGTH_SHORT).show();
                        if (host != null) {
                            host.submit(h -> h.refresh());
                        }
                    }
            ));
        }
        sections.add(new GboardPatchesSettingsContract.Section(
                "GBOARD UI (BETA)",
                themeRows
        ));

        List<GboardPatchesSettingsContract.StatusBlock> statusBlocks = new ArrayList<>();
        statusBlocks.add(new GboardPatchesSettingsContract.StatusBlock(
                "Custom Appearance",
                "Personalize your Gboard Pro experience with custom adaptive launcher icons and Material 3 Expressive UI tones.",
                GboardPatchesSettingsContract.StatusTone.INFO
        ));

        return new GboardPatchesSettingsContract.Screen(
                "Appearance",
                "Beta",
                "Appearance",
                "App icons & styling",
                statusBlocks,
                sections
        );
    }

    private void applyAppIcon(Context ctx, String targetAlias) {
        try {
            SharedPreferences prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            prefs.edit().putString(KEY_SELECTED_ICON, targetAlias).apply();

            PackageManager pm = ctx.getPackageManager();
            String pkg = ctx.getPackageName();

            String[] allAliases = {
                    "launcher.Default",
                    "launcher.Purple",
                    "launcher.Slate",
                    "launcher.ForestGreen",
                    "launcher.NavyBlue"
            };

            for (String alias : allAliases) {
                ComponentName comp = new ComponentName(pkg, pkg + "." + alias);
                int newState = alias.equals(targetAlias)
                        ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                        : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                try {
                    pm.setComponentEnabledSetting(comp, newState, PackageManager.DONT_KILL_APP);
                } catch (Throwable ignored) {
                }
            }
            Toast.makeText(ctx, "Icon updated! Launcher will refresh shortly.", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(ctx, "Icon preference saved.", Toast.LENGTH_SHORT).show();
        }
    }
}
