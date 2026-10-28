package com.akshaykadam.pixelboard.extension.settings;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class GboardM3Helper {

    public static final class Palette {
        public final int windowBackground;
        public final int surface;
        public final int surfaceAlt;
        public final int surfaceStroke;
        public final int textPrimary;
        public final int textSecondary;
        public final int accent;
        public final int accentContainer;
        public final int onAccentContainer;
        public final int ripple;
        public final boolean isNight;

        public Palette(int windowBackground, int surface, int surfaceAlt, int surfaceStroke,
                       int textPrimary, int textSecondary, int accent, int accentContainer,
                       int onAccentContainer, int ripple, boolean isNight) {
            this.windowBackground = windowBackground;
            this.surface = surface;
            this.surfaceAlt = surfaceAlt;
            this.surfaceStroke = surfaceStroke;
            this.textPrimary = textPrimary;
            this.textSecondary = textSecondary;
            this.accent = accent;
            this.accentContainer = accentContainer;
            this.onAccentContainer = onAccentContainer;
            this.ripple = ripple;
            this.isNight = isNight;
        }

        public static Palette forContext(Context context) {
            Configuration config = context.getResources().getConfiguration();
            boolean night = (config.uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                try {
                    int bg = context.getColor(night ? android.R.color.system_neutral1_900 : android.R.color.system_neutral1_50);
                    int surf = context.getColor(night ? android.R.color.system_neutral1_800 : android.R.color.system_neutral1_10);
                    int surfAlt = context.getColor(night ? android.R.color.system_neutral1_700 : android.R.color.system_neutral1_100);
                    int stroke = blendAlpha(context.getColor(night ? android.R.color.system_neutral2_700 : android.R.color.system_neutral2_200), 0.5f);
                    int textP = context.getColor(night ? android.R.color.system_neutral1_100 : android.R.color.system_neutral1_900);
                    int textS = context.getColor(night ? android.R.color.system_neutral2_200 : android.R.color.system_neutral2_700);
                    int acc = context.getColor(night ? android.R.color.system_accent1_200 : android.R.color.system_accent1_600);
                    int accCont = context.getColor(night ? android.R.color.system_accent1_700 : android.R.color.system_accent1_100);
                    int onAccCont = context.getColor(night ? android.R.color.system_accent1_100 : android.R.color.system_accent1_900);
                    int rip = blendAlpha(acc, night ? 0.20f : 0.12f);
                    return new Palette(bg, surf, surfAlt, stroke, textP, textS, acc, accCont, onAccCont, rip, night);
                } catch (Throwable ignored) {
                }
            }

            if (night) {
                return new Palette(
                        0xFF121316, // windowBackground
                        0xFF1E1F24, // surface
                        0xFF2A2B32, // surfaceAlt
                        0x33FFFFFF, // surfaceStroke
                        0xFFE3E2E6, // textPrimary
                        0xFFC4C6D0, // textSecondary
                        0xFFA8C7FA, // accent
                        0xFF004A77, // accentContainer
                        0xFFC2E7FF, // onAccentContainer
                        0x33A8C7FA, // ripple
                        true
                );
            } else {
                return new Palette(
                        0xFFFDFCFF, // windowBackground
                        0xFFF0F4F9, // surface
                        0xFFE3E8EF, // surfaceAlt
                        0x1F000000, // surfaceStroke
                        0xFF1A1C1E, // textPrimary
                        0xFF44474E, // textSecondary
                        0xFF0B57D0, // accent
                        0xFFD3E3FD, // accentContainer
                        0xFF041E49, // onAccentContainer
                        0x260B57D0, // ripple
                        false
                );
            }
        }
    }

    public static int dp(Context context, int dpValue) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                context.getResources().getDisplayMetrics()
        ));
    }

    public static int blendAlpha(int color, float alpha) {
        int a = Math.round(Color.alpha(color) * alpha);
        return Color.argb(a, Color.red(color), Color.green(color), Color.blue(color));
    }

    public static void setupSystemBars(Activity activity, Palette palette) {
        Window window = activity.getWindow();
        window.setStatusBarColor(palette.windowBackground);
        window.setNavigationBarColor(palette.windowBackground);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController controller = window.getInsetsController();
            if (controller != null) {
                int appearance = !palette.isNight
                        ? (WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS | WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS)
                        : 0;
                controller.setSystemBarsAppearance(
                        appearance,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS | WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                );
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            int flags = decor.getSystemUiVisibility();
            if (!palette.isNight) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
            }
            decor.setSystemUiVisibility(flags);
        }
    }

    public static View createToolbar(final Activity activity, String title, Palette palette) {
        LinearLayout toolbar = new LinearLayout(activity);
        toolbar.setOrientation(LinearLayout.HORIZONTAL);
        toolbar.setGravity(Gravity.CENTER_VERTICAL);
        toolbar.setPadding(dp(activity, 8), dp(activity, 12), dp(activity, 16), dp(activity, 12));
        toolbar.setBackgroundColor(palette.windowBackground);

        // Back button
        ImageView backBtn = new ImageView(activity);
        backBtn.setContentDescription("Navigate up");
        GradientDrawable backBg = new GradientDrawable();
        backBg.setShape(GradientDrawable.OVAL);
        backBg.setColor(Color.TRANSPARENT);
        backBtn.setBackground(new RippleDrawable(ColorStateList.valueOf(palette.ripple), backBg, null));
        backBtn.setPadding(dp(activity, 10), dp(activity, 10), dp(activity, 10), dp(activity, 10));

        // Draw back arrow
        backBtn.setImageDrawable(createBackArrowDrawable(activity, palette.textPrimary));
        LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(dp(activity, 44), dp(activity, 44));
        btnLp.rightMargin = dp(activity, 8);
        toolbar.addView(backBtn, btnLp);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                applyActivityExitTransition(activity);
            }
        });

        // Title
        TextView titleView = new TextView(activity);
        titleView.setText(title);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        titleView.setTextColor(palette.textPrimary);
        titleView.setSingleLine(true);
        toolbar.addView(titleView, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f
        ));

        return toolbar;
    }

    public static Drawable createBackArrowDrawable(Context context, final int color) {
        final float density = context.getResources().getDisplayMetrics().density;
        return new Drawable() {
            @Override
            public void draw(android.graphics.Canvas canvas) {
                android.graphics.Paint paint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
                paint.setColor(color);
                paint.setStyle(android.graphics.Paint.Style.STROKE);
                paint.setStrokeWidth(2.2f * density);
                paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
                paint.setStrokeJoin(android.graphics.Paint.Join.ROUND);

                android.graphics.Rect b = getBounds();
                float cx = b.exactCenterX();
                float cy = b.exactCenterY();
                float r = 7f * density;

                // arrow shaft
                canvas.drawLine(cx - r, cy, cx + r, cy, paint);
                // upper barb
                canvas.drawLine(cx - r, cy, cx - r + (5f * density), cy - (5f * density), paint);
                // lower barb
                canvas.drawLine(cx - r, cy, cx - r + (5f * density), cy + (5f * density), paint);
            }

            @Override
            public void setAlpha(int alpha) {}
            @Override
            public void setColorFilter(android.graphics.ColorFilter colorFilter) {}
            @Override
            public int getOpacity() { return android.graphics.PixelFormat.TRANSLUCENT; }
        };
    }

    public static GradientDrawable createCardDrawable(int bgColor, int strokeColor, int cornerRadiusPx) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(bgColor);
        gd.setCornerRadius(cornerRadiusPx);
        if (strokeColor != Color.TRANSPARENT) {
            gd.setStroke(1, strokeColor);
        }
        return gd;
    }

    public static Drawable createClickableCardDrawable(int bgColor, int strokeColor, int rippleColor, int cornerRadiusPx) {
        GradientDrawable content = createCardDrawable(bgColor, strokeColor, cornerRadiusPx);
        GradientDrawable mask = createCardDrawable(Color.WHITE, Color.TRANSPARENT, cornerRadiusPx);
        return new RippleDrawable(ColorStateList.valueOf(rippleColor), content, mask);
    }

    public static void applyActivityEnterTransition(Activity activity) {
        try {
            int enterAnim = activity.getResources().getIdentifier("m3_slide_in_right", "anim", activity.getPackageName());
            int exitAnim = activity.getResources().getIdentifier("m3_slide_out_left", "anim", activity.getPackageName());
            if (enterAnim != 0 && exitAnim != 0) {
                activity.overridePendingTransition(enterAnim, exitAnim);
            }
        } catch (Throwable ignored) {
        }
    }

    public static void applyActivityExitTransition(Activity activity) {
        try {
            int enterAnim = activity.getResources().getIdentifier("m3_slide_in_left", "anim", activity.getPackageName());
            int exitAnim = activity.getResources().getIdentifier("m3_slide_out_right", "anim", activity.getPackageName());
            if (enterAnim != 0 && exitAnim != 0) {
                activity.overridePendingTransition(enterAnim, exitAnim);
            }
        } catch (Throwable ignored) {
        }
    }
}
