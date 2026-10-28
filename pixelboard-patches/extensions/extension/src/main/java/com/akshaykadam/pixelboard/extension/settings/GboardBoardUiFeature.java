package com.akshaykadam.pixelboard.extension.settings;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public final class GboardBoardUiFeature implements GboardPatchesSettingsContract.Feature {

    public static final String PREF_NAME = "gboard_pro_board_ui_prefs";
    public static final String KEY_ROUNDED_KEYS = "rounded_keycaps_enabled";
    public static final String KEY_KEY_BORDERS = "key_borders_enabled";
    public static final String KEY_AMOLED_BLACK = "amoled_black_enabled";
    public static final String KEY_COMPACT_PADDING = "compact_padding_enabled";

    private final Context context;

    public GboardBoardUiFeature(Context context) {
        this.context = context;
    }

    @Override
    public String getEntryTitle() {
        return "Board UI";
    }

    @Override
    public String getEntrySummary() {
        return "Custom keycaps, borders & styles";
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

        boolean roundedKeys = prefs.getBoolean(KEY_ROUNDED_KEYS, true);
        boolean keyBorders = prefs.getBoolean(KEY_KEY_BORDERS, true);
        boolean amoledBlack = prefs.getBoolean(KEY_AMOLED_BLACK, false);
        boolean compactPadding = prefs.getBoolean(KEY_COMPACT_PADDING, false);

        List<GboardPatchesSettingsContract.Section> sections = new ArrayList<>();

        List<GboardPatchesSettingsContract.Row> rows = new ArrayList<>();
        rows.add(new GboardPatchesSettingsContract.ToggleRow(
                "Rounded Keycaps",
                "Apply modern pill-shaped rounded geometry to all typing keys",
                true,
                roundedKeys,
                value -> {
                    prefs.edit().putBoolean(KEY_ROUNDED_KEYS, value).apply();
                    if (host != null) host.submit(h -> h.refresh());
                }
        ));
        rows.add(new GboardPatchesSettingsContract.ToggleRow(
                "High-Contrast Key Borders",
                "Display distinct Material 3 key outlines for enhanced key separation",
                true,
                keyBorders,
                value -> {
                    prefs.edit().putBoolean(KEY_KEY_BORDERS, value).apply();
                    if (host != null) host.submit(h -> h.refresh());
                }
        ));
        rows.add(new GboardPatchesSettingsContract.ToggleRow(
                "AMOLED Pure Black Mode",
                "Pitch black keyboard background for battery savings on OLED screens",
                true,
                amoledBlack,
                value -> {
                    prefs.edit().putBoolean(KEY_AMOLED_BLACK, value).apply();
                    if (host != null) host.submit(h -> h.refresh());
                }
        ));
        rows.add(new GboardPatchesSettingsContract.ToggleRow(
                "Compact Key Margins",
                "Slightly reduce keyboard height and spacing for wider screen visibility",
                true,
                compactPadding,
                value -> {
                    prefs.edit().putBoolean(KEY_COMPACT_PADDING, value).apply();
                    if (host != null) host.submit(h -> h.refresh());
                }
        ));

        sections.add(new GboardPatchesSettingsContract.Section(
                "KEYBOARD STYLING",
                rows
        ));

        List<GboardPatchesSettingsContract.StatusBlock> statusBlocks = new ArrayList<>();
        statusBlocks.add(new GboardPatchesSettingsContract.StatusBlock(
                "Board UI Customization",
                "Fine-tune keyboard layouts, key shape radiuses, and tactile border outlines.",
                GboardPatchesSettingsContract.StatusTone.INFO
        ));

        return new GboardPatchesSettingsContract.Screen(
                "Board UI",
                null,
                "Board UI",
                "Custom keycaps, borders & styles",
                statusBlocks,
                sections
        );
    }
}
