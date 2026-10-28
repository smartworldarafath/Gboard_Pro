package com.akshaykadam.pixelboard.extension.settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public final class GboardAboutFeature implements GboardPatchesSettingsContract.Feature {

    private final Context context;

    public GboardAboutFeature(Context context) {
        this.context = context;
    }

    @Override
    public String getEntryTitle() {
        return "About";
    }

    @Override
    public String getEntrySummary() {
        return "Developer & project details";
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

        List<GboardPatchesSettingsContract.Section> sections = new ArrayList<>();

        // 1. Project Info Section
        List<GboardPatchesSettingsContract.Row> infoRows = new ArrayList<>();
        infoRows.add(new GboardPatchesSettingsContract.DetailRow(
                "Application",
                "Gboard Pro (Enhanced Edition)",
                false
        ));
        infoRows.add(new GboardPatchesSettingsContract.DetailRow(
                "Version",
                "v1.0.5 (Beta)",
                false
        ));
        infoRows.add(new GboardPatchesSettingsContract.DetailRow(
                "Maintainer & Developer",
                "Arafath Rahman (@smartworldarafath)",
                false
        ));
        infoRows.add(new GboardPatchesSettingsContract.DetailRow(
                "License",
                "GNU General Public License v3.0 (GPLv3)",
                false
        ));
        sections.add(new GboardPatchesSettingsContract.Section(
                "PROJECT INFORMATION",
                infoRows
        ));

        // 2. Links & Community Section
        List<GboardPatchesSettingsContract.Row> linkRows = new ArrayList<>();
        linkRows.add(new GboardPatchesSettingsContract.CommandRow(
                "GitHub Repository",
                "smartworldarafath/Gboard_Pro",
                true,
                () -> openUrl(ctx, "https://github.com/smartworldarafath/Gboard_Pro")
        ));
        linkRows.add(new GboardPatchesSettingsContract.CommandRow(
                "Report Issue or Request Feature",
                "Submit feedback and bug reports on GitHub",
                true,
                () -> openUrl(ctx, "https://github.com/smartworldarafath/Gboard_Pro/issues")
        ));
        linkRows.add(new GboardPatchesSettingsContract.CommandRow(
                "Developer Telegram",
                "@smartworldarafath",
                true,
                () -> openUrl(ctx, "https://t.me/smartworldarafath")
        ));
        sections.add(new GboardPatchesSettingsContract.Section(
                "COMMUNITY & SUPPORT",
                linkRows
        ));

        List<GboardPatchesSettingsContract.StatusBlock> statusBlocks = new ArrayList<>();
        statusBlocks.add(new GboardPatchesSettingsContract.StatusBlock(
                "Gboard Pro",
                "A modern, high-performance Gboard enhancement with Material 3 Expressive styling, 144Hz refresh rate, Gemini Voice, and in-app updates.",
                GboardPatchesSettingsContract.StatusTone.INFO
        ));

        return new GboardPatchesSettingsContract.Screen(
                "About",
                "v1.0.5",
                "About",
                "Developer & project details",
                statusBlocks,
                sections
        );
    }

    private void openUrl(Context ctx, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        } catch (Throwable ignored) {
        }
    }
}
