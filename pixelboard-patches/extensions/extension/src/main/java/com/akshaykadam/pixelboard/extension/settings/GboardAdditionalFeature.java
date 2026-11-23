package com.akshaykadam.pixelboard.extension.settings;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Additional hub containing Notification, Permissions, Reset, and Beta Program sub-sections.
 */
public final class GboardAdditionalFeature implements GboardPatchesSettingsContract.Feature {

    private final Context context;

    public GboardAdditionalFeature(Context context) {
        this.context = context;
    }

    @Override
    public String getEntryTitle() {
        return "Additional";
    }

    @Override
    public String getEntrySummary() {
        return "Notification, Permissions, Reset & Beta Program";
    }

    @Override
    public boolean isAvailable(Context context) {
        return true;
    }

    @Override
    public List<GboardPatchesSettingsContract.Feature> getNavigationChildren() {
        return Arrays.asList(
                new TestingModeFeature(context, "Notification", "Manage keyboard notifications & alerts"),
                new TestingModeFeature(context, "Permissions", "Review runtime & system permissions"),
                new TestingModeFeature(context, "Reset", "Reset Gboard Pro settings & caches"),
                new TestingModeFeature(context, "Beta Program", "Early access & experimental releases")
        );
    }

    @Override
    public void onVisible(GboardPatchesSettingsContract.FeatureHost host) {
    }

    @Override
    public GboardPatchesSettingsContract.Screen buildScreen(GboardPatchesSettingsContract.FeatureHost host) {
        final Context ctx = host != null && host.getContext() != null ? host.getContext() : context;

        List<GboardPatchesSettingsContract.Row> rows = new ArrayList<>();
        rows.add(new GboardPatchesSettingsContract.NavigationRow(
                "Notification",
                "Manage keyboard notifications & alerts",
                true,
                () -> GboardPatchesSettingsContract.openFeature(
                        host, new TestingModeFeature(ctx, "Notification",
                                "Manage keyboard notifications & alerts"))
        ));
        rows.add(new GboardPatchesSettingsContract.NavigationRow(
                "Permissions",
                "Review runtime & system permissions",
                true,
                () -> GboardPatchesSettingsContract.openFeature(
                        host, new TestingModeFeature(ctx, "Permissions",
                                "Review runtime & system permissions"))
        ));
        rows.add(new GboardPatchesSettingsContract.NavigationRow(
                "Reset",
                "Reset Gboard Pro settings & caches",
                true,
                () -> GboardPatchesSettingsContract.openFeature(
                        host, new TestingModeFeature(ctx, "Reset",
                                "Reset Gboard Pro settings & caches"))
        ));
        rows.add(new GboardPatchesSettingsContract.NavigationRow(
                "Beta Program",
                "Early access & experimental releases",
                true,
                () -> GboardPatchesSettingsContract.openFeature(
                        host, new TestingModeFeature(ctx, "Beta Program",
                                "Early access & experimental releases"))
        ));

        List<GboardPatchesSettingsContract.Section> sections = new ArrayList<>();
        sections.add(new GboardPatchesSettingsContract.Section("ADDITIONAL", rows));

        return new GboardPatchesSettingsContract.Screen(
                "Additional",
                null,
                "Additional",
                "Additional tools, preferences & testing modules",
                Collections.emptyList(),
                sections,
                GboardPatchesSettingsContract.RefreshPolicy.none(),
                GboardPatchesSettingsContract.PanelStyle.FLAT,
                null,
                false
        );
    }

    /**
     * Child placeholder screen shown for Additional items currently under testing mode.
     */
    public static final class TestingModeFeature implements GboardPatchesSettingsContract.Feature {

        private final Context context;
        private final String sectionTitle;
        private final String entrySummary;

        public TestingModeFeature(Context context, String sectionTitle, String entrySummary) {
            this.context = context;
            this.sectionTitle = sectionTitle;
            this.entrySummary = entrySummary;
        }

        @Override
        public String getEntryTitle() {
            return sectionTitle;
        }

        @Override
        public String getEntrySummary() {
            return entrySummary;
        }

        @Override
        public boolean isAvailable(Context context) {
            return true;
        }

        @Override
        public void onVisible(GboardPatchesSettingsContract.FeatureHost host) {
        }

        @Override
        public GboardPatchesSettingsContract.Screen buildScreen(
                GboardPatchesSettingsContract.FeatureHost host) {
            List<GboardPatchesSettingsContract.Row> rows = new ArrayList<>();
            rows.add(new GboardPatchesSettingsContract.DetailRow(
                    "Functions under testing mode",
                    sectionTitle + " controls are currently under testing and will be activated in a future Gboard Pro release.",
                    true
            ));

            List<GboardPatchesSettingsContract.Section> sections = new ArrayList<>();
            sections.add(new GboardPatchesSettingsContract.Section("STATUS", rows));

            return new GboardPatchesSettingsContract.Screen(
                    sectionTitle,
                    null,
                    sectionTitle,
                    entrySummary,
                    Collections.emptyList(),
                    sections,
                    GboardPatchesSettingsContract.RefreshPolicy.none(),
                    GboardPatchesSettingsContract.PanelStyle.FLAT,
                    null,
                    false
            );
        }
    }
}
