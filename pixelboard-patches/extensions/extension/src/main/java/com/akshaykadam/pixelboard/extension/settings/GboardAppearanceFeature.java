package com.akshaykadam.pixelboard.extension.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GboardAppearanceFeature implements GboardPatchesSettingsContract.Feature {

    public static final String PREF_NAME = "gboard_pro_appearance_prefs";
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
        return "Material 3 Expressive UI theme palettes";
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

        String currentTheme = prefs.getString(KEY_UI_THEME, "blush_lilac");
        float density = ctx.getResources().getDisplayMetrics().density;

        List<GboardPatchesSettingsContract.Section> sections = new ArrayList<>();

        // Info Card explaining the Theme & Color palette
        List<GboardPatchesSettingsContract.Row> infoRows = new ArrayList<>();
        infoRows.add(new GboardPatchesSettingsContract.InfoRow(
                "Gboard UI Vibes",
                "Select a Google Material 3 Expressive color palette. The signature Blush Lilac replicates the exact light pink pastel look from showcase.png.",
                true));
        sections.add(new GboardPatchesSettingsContract.Section("PALETTE PREVIEW", infoRows));

        // Theme Palette Cards
        List<GboardPatchesSettingsContract.Row> themeRows = new ArrayList<>();

        String[] themeKeys = {
                "blush_lilac",
                "cotton_candy",
                "rainbow",
                "botanical",
                "default"
        };
        String[] themeTitles = {
                "Blush Lilac (Showcase Pink)",
                "Cotton Candy (Pastel Rose)",
                "Sunset Peach (Warm Apricot)",
                "Fresh Mint (Sage Teal)",
                "Default (System Dynamic)"
        };
        String[] themeSummaries = {
                "Signature light pink background with vibrant violet-purple accents",
                "Gentle pastel rose pink & airy sky tones",
                "Warm glowing peach & soft melon pink",
                "Botanical garden sage & fresh mint",
                "Material You dynamic wallpaper-based accent tones"
        };
        int[][] themeSwatches = {
                {0xFF7E57C2, 0xFFEBDDFB, 0xFFD0BCFF, 0xFFF8F2FA},
                {0xFFD81B60, 0xFFFCE4EC, 0xFFFF80AB, 0xFFFDF2F7},
                {0xFFE64A19, 0xFFFFCCBC, 0xFFFF8A65, 0xFFFFF5F2},
                {0xFF00796B, 0xFFC8E6C9, 0xFF4DB6AC, 0xFFF2F8F5},
                {0xFF1A73E8, 0xFFE8F0FE, 0xFF4285F4, 0xFFF1F3F4}
        };

        for (int i = 0; i < themeKeys.length; i++) {
            final String key = themeKeys[i];
            boolean selected = key.equals(currentTheme);
            Drawable swatchDrawable = createPaletteSwatchDrawable(themeSwatches[i], 48, 48, density);

            themeRows.add(new GboardPatchesSettingsContract.RadioRow(
                    themeTitles[i],
                    themeSummaries[i],
                    true,
                    selected,
                    () -> {
                        prefs.edit().putString(KEY_UI_THEME, key).apply();
                        try {
                            android.preference.PreferenceManager.getDefaultSharedPreferences(ctx)
                                    .edit().putString(KEY_UI_THEME, key).apply();
                        } catch (Throwable ignored) {}
                        Toast.makeText(ctx, "Applied " + key + " theme", Toast.LENGTH_SHORT).show();
                        if (host != null) {
                            host.submit(h -> h.refresh());
                        }
                    },
                    swatchDrawable));
        }

        sections.add(new GboardPatchesSettingsContract.Section("GBOARD UI (BETA)", themeRows));

        return new GboardPatchesSettingsContract.Screen(
                "Appearance",
                "Pro",
                "Appearance",
                "Personalize theme colors & visual styling",
                Collections.emptyList(),
                sections);
    }

    private static Drawable createPaletteSwatchDrawable(int[] colors, int widthDp, int heightDp, float density) {
        int w = Math.round(widthDp * density);
        int h = Math.round(heightDp * density);
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Path clipPath = new Path();
        float corner = 12f * density;
        clipPath.addRoundRect(new RectF(0, 0, w, h), corner, corner, Path.Direction.CW);
        canvas.clipPath(clipPath);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        float segW = (float) w / colors.length;
        for (int i = 0; i < colors.length; i++) {
            paint.setColor(colors[i]);
            canvas.drawRect(i * segW, 0, (i + 1) * segW, h, paint);
        }

        Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1.5f * density);
        strokePaint.setColor(0x28000000);
        canvas.drawRoundRect(new RectF(1, 1, w - 1, h - 1), corner, corner, strokePaint);

        return new BitmapDrawable(null, bitmap);
    }
}
