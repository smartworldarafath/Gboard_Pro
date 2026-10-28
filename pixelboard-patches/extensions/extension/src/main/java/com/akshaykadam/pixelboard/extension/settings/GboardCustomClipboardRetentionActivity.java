package com.akshaykadam.pixelboard.extension.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GboardCustomClipboardRetentionActivity extends Activity {

    public static final String KEY_CLIPBOARD_RETENTION = "clipboard_history_retention";
    public static final String KEY_CUSTOM_RETENTION = "custom_clipboard_history_retention";

    public static final class RetentionPreset {
        public final String id;
        public final String label;
        public final String subtitle;
        public final long hours;

        public RetentionPreset(String id, String label, String subtitle, long hours) {
            this.id = id;
            this.label = label;
            this.subtitle = subtitle;
            this.hours = hours;
        }
    }

    private GboardM3Helper.Palette palette;
    private final List<RetentionPreset> presets = new ArrayList<RetentionPreset>();
    private TextView activeStatusView;
    private LinearLayout presetsContainer;
    private EditText customInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(GboardM3Helper.resolveActivityTheme(this));
        super.onCreate(savedInstanceState);

        try {
            palette = GboardM3Helper.Palette.forContext(this);
            GboardM3Helper.initActivity(this, palette);

            initPresets();

            LinearLayout root = new LinearLayout(this);
            root.setOrientation(LinearLayout.VERTICAL);
            root.setBackgroundColor(palette.windowBackground);
            root.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            root.addView(GboardM3Helper.createToolbar(this, "Clipboard Retention", palette));

            ScrollView scrollView = new ScrollView(this);
            scrollView.setFillViewport(true);
            scrollView.setVerticalScrollBarEnabled(false);

            LinearLayout content = new LinearLayout(this);
            content.setOrientation(LinearLayout.VERTICAL);
            content.setPadding(GboardM3Helper.dp(this, 16), GboardM3Helper.dp(this, 8),
                    GboardM3Helper.dp(this, 16), GboardM3Helper.dp(this, 28));

            // 1. Hero Card
            content.addView(buildHeroCard());

            // 2. Preset List Section
            TextView presetLabel = new TextView(this);
            presetLabel.setText("PRESET RETENTION TIMES");
            presetLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
            presetLabel.setTypeface(Typeface.DEFAULT_BOLD);
            presetLabel.setTextColor(palette.accent);
            presetLabel.setPadding(GboardM3Helper.dp(this, 8), GboardM3Helper.dp(this, 20),
                    GboardM3Helper.dp(this, 8), GboardM3Helper.dp(this, 8));
            content.addView(presetLabel);

            presetsContainer = new LinearLayout(this);
            presetsContainer.setOrientation(LinearLayout.VERTICAL);
            content.addView(presetsContainer);

            // 3. Custom Input Section
            TextView customSectionLabel = new TextView(this);
            customSectionLabel.setText("CUSTOM TIME IN HOURS");
            customSectionLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
            customSectionLabel.setTypeface(Typeface.DEFAULT_BOLD);
            customSectionLabel.setTextColor(palette.accent);
            customSectionLabel.setPadding(GboardM3Helper.dp(this, 8), GboardM3Helper.dp(this, 24),
                    GboardM3Helper.dp(this, 8), GboardM3Helper.dp(this, 8));
            content.addView(customSectionLabel);

            content.addView(buildCustomInputCard());

            renderPresets();

            scrollView.addView(content);
            root.addView(scrollView, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            setContentView(root);
        } catch (Throwable t) {
            fallbackView(t);
        }
    }

    private void fallbackView(Throwable t) {
        try {
            LinearLayout fallback = new LinearLayout(this);
            fallback.setOrientation(LinearLayout.VERTICAL);
            fallback.setPadding(32, 64, 32, 32);
            TextView tv = new TextView(this);
            tv.setText("Clipboard Retention Settings\n\nConfigure clipboard history retention limits.");
            tv.setTextSize(16f);
            fallback.addView(tv);
            setContentView(fallback);
        } catch (Throwable ignored) {
        }
    }

    private void initPresets() {
        presets.clear();
        presets.add(new RetentionPreset("1", "1 Hour", "Default stock Gboard retention limit", 1));
        presets.add(new RetentionPreset("6", "6 Hours", "Half-day temporary retention", 6));
        presets.add(new RetentionPreset("12", "12 Hours", "Daytime work session retention", 12));
        presets.add(new RetentionPreset("24", "24 Hours (1 Day)", "Keep clipboard items for a full day", 24));
        presets.add(new RetentionPreset("48", "48 Hours (2 Days)", "Weekend retention for active workflows", 48));
        presets.add(new RetentionPreset("72", "72 Hours (3 Days)", "Extended 3-day history", 72));
        presets.add(new RetentionPreset("168", "168 Hours (1 Week)", "Preserve clips throughout the entire week", 168));
        presets.add(new RetentionPreset("720", "720 Hours (1 Month)", "Long-term monthly history retention", 720));
        presets.add(new RetentionPreset("8760", "8760 Hours (1 Year)", "Permanent clipboard memory (indefinite)", 8760));
    }

    private View buildHeroCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackground(GboardM3Helper.createCardDrawable(
                palette.surface, palette.surfaceStroke, GboardM3Helper.dp(this, 20)));
        card.setPadding(GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 18),
                GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 18));

        TextView title = new TextView(this);
        title.setText("Clipboard History Retention");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.5f);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(palette.textPrimary);
        card.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Select or specify the exact duration unpinned items remain in your Gboard Pro clipboard before automatic cleanup.");
        subtitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.5f);
        subtitle.setTextColor(palette.textSecondary);
        subtitle.setLineSpacing(GboardM3Helper.dp(this, 2), 1.15f);
        LinearLayout.LayoutParams subLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subLp.topMargin = GboardM3Helper.dp(this, 6);
        card.addView(subtitle, subLp);

        // Active Status indicator pill
        activeStatusView = new TextView(this);
        activeStatusView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        activeStatusView.setTypeface(Typeface.DEFAULT_BOLD);
        activeStatusView.setTextColor(palette.onAccentContainer);
        activeStatusView.setBackground(GboardM3Helper.createCardDrawable(
                palette.accentContainer, Color.TRANSPARENT, GboardM3Helper.dp(this, 10)));
        activeStatusView.setPadding(GboardM3Helper.dp(this, 12), GboardM3Helper.dp(this, 6),
                GboardM3Helper.dp(this, 12), GboardM3Helper.dp(this, 6));

        LinearLayout.LayoutParams statusLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        statusLp.topMargin = GboardM3Helper.dp(this, 12);
        card.addView(activeStatusView, statusLp);

        updateActiveStatusText();
        return card;
    }

    private View buildCustomInputCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackground(GboardM3Helper.createCardDrawable(
                palette.surface, palette.surfaceStroke, GboardM3Helper.dp(this, 20)));
        card.setPadding(GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 18),
                GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 18));

        TextView label = new TextView(this);
        label.setText("Enter Specific Custom Retention");
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        label.setTextColor(palette.textPrimary);
        card.addView(label);

        TextView hint = new TextView(this);
        hint.setText("Type any number of hours (e.g., 36 for 1.5 days, 120 for 5 days):");
        hint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        hint.setTextColor(palette.textSecondary);
        LinearLayout.LayoutParams hintLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hintLp.topMargin = GboardM3Helper.dp(this, 4);
        card.addView(hint, hintLp);

        // Input row
        LinearLayout inputRow = new LinearLayout(this);
        inputRow.setOrientation(LinearLayout.HORIZONTAL);
        inputRow.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams rowLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rowLp.topMargin = GboardM3Helper.dp(this, 12);
        inputRow.setLayoutParams(rowLp);

        customInput = new EditText(this);
        customInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        customInput.setHint("Hours");
        customInput.setHintTextColor(palette.textSecondary);
        customInput.setTextColor(palette.textPrimary);
        customInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        customInput.setBackground(GboardM3Helper.createCardDrawable(
                palette.surfaceAlt, palette.surfaceStroke, GboardM3Helper.dp(this, 12)));
        customInput.setPadding(GboardM3Helper.dp(this, 14), GboardM3Helper.dp(this, 10),
                GboardM3Helper.dp(this, 14), GboardM3Helper.dp(this, 10));

        inputRow.addView(customInput, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        View spacer = new View(this);
        inputRow.addView(spacer, new LinearLayout.LayoutParams(GboardM3Helper.dp(this, 10), 1));

        Button applyBtn = new Button(this);
        applyBtn.setText("Apply");
        applyBtn.setTextColor(palette.isNight ? 0xFF00325B : 0xFFFFFFFF);
        applyBtn.setTypeface(Typeface.DEFAULT_BOLD);
        applyBtn.setBackground(GboardM3Helper.createClickableCardDrawable(
                palette.accent, Color.TRANSPARENT, palette.ripple, GboardM3Helper.dp(this, 14)));
        applyBtn.setPadding(GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 10),
                GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 10));

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyCustomInput();
            }
        });

        inputRow.addView(applyBtn, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        card.addView(inputRow);
        return card;
    }

    private void renderPresets() {
        presetsContainer.removeAllViews();
        long currentHours = getCurrentRetentionHours();

        for (final RetentionPreset preset : presets) {
            boolean isSelected = (preset.hours == currentHours);

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setGravity(Gravity.CENTER_VERTICAL);

            int bgColor = isSelected ? palette.accentContainer : palette.surface;
            int strokeColor = isSelected ? palette.accent : palette.surfaceStroke;
            card.setBackground(GboardM3Helper.createClickableCardDrawable(
                    bgColor, strokeColor, palette.ripple, GboardM3Helper.dp(this, 16)));
            card.setPadding(GboardM3Helper.dp(this, 16), GboardM3Helper.dp(this, 12),
                    GboardM3Helper.dp(this, 16), GboardM3Helper.dp(this, 12));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.bottomMargin = GboardM3Helper.dp(this, 8);
            card.setLayoutParams(lp);

            LinearLayout textCol = new LinearLayout(this);
            textCol.setOrientation(LinearLayout.VERTICAL);

            TextView label = new TextView(this);
            label.setText(preset.label);
            label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            label.setTypeface(Typeface.DEFAULT_BOLD);
            label.setTextColor(isSelected ? palette.onAccentContainer : palette.textPrimary);
            textCol.addView(label);

            TextView sub = new TextView(this);
            sub.setText(preset.subtitle);
            sub.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.5f);
            sub.setTextColor(isSelected ? palette.onAccentContainer : palette.textSecondary);
            LinearLayout.LayoutParams subLp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            subLp.topMargin = GboardM3Helper.dp(this, 2);
            textCol.addView(sub, subLp);

            card.addView(textCol, new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

            if (isSelected) {
                TextView activeBadge = new TextView(this);
                activeBadge.setText("Active");
                activeBadge.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f);
                activeBadge.setTypeface(Typeface.DEFAULT_BOLD);
                activeBadge.setTextColor(palette.onAccentContainer);
                activeBadge.setBackground(GboardM3Helper.createCardDrawable(
                        GboardM3Helper.blendAlpha(palette.accent, 0.25f), Color.TRANSPARENT, GboardM3Helper.dp(this, 8)));
                activeBadge.setPadding(GboardM3Helper.dp(this, 8), GboardM3Helper.dp(this, 4),
                        GboardM3Helper.dp(this, 8), GboardM3Helper.dp(this, 4));
                card.addView(activeBadge);
            }

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveRetentionHours(preset.hours);
                }
            });

            presetsContainer.addView(card);
        }
    }

    private long getCurrentRetentionHours() {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String val = prefs.getString(KEY_CLIPBOARD_RETENTION, "1");
            return Long.parseLong(val);
        } catch (Throwable t) {
            return 1L;
        }
    }

    private void updateActiveStatusText() {
        long hours = getCurrentRetentionHours();
        String formatted;
        if (hours < 24) {
            formatted = hours + (hours == 1 ? " Hour" : " Hours");
        } else if (hours % 24 == 0) {
            long days = hours / 24;
            formatted = days + (days == 1 ? " Day (" : " Days (") + hours + " Hours)";
        } else {
            formatted = hours + " Hours (~" + String.format("%.1f", (float) hours / 24f) + " Days)";
        }
        activeStatusView.setText("Active Retention: " + formatted);
    }

    private void saveRetentionHours(long hours) {
        if (hours <= 0) {
            Toast.makeText(this, "Retention must be at least 1 hour", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit()
                    .putString(KEY_CLIPBOARD_RETENTION, String.valueOf(hours))
                    .putLong(KEY_CUSTOM_RETENTION, hours)
                    .apply();

            Toast.makeText(this, "Clipboard retention set to " + hours + " hours", Toast.LENGTH_SHORT).show();
            updateActiveStatusText();
            renderPresets();
        } catch (Throwable t) {
            Toast.makeText(this, "Error saving preference: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void applyCustomInput() {
        String inputStr = customInput.getText().toString().trim();
        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Please enter hours", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            long h = Long.parseLong(inputStr);
            if (h < 1 || h > 87600) {
                Toast.makeText(this, "Please enter a value between 1 and 87600 hours", Toast.LENGTH_SHORT).show();
                return;
            }
            saveRetentionHours(h);
            customInput.setText("");
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number entered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GboardM3Helper.applyActivityExitTransition(this);
    }
}
