package com.akshaykadam.pixelboard.extension.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class GboardM3Helper {

    public static final String PREF_APPEARANCE = "gboard_pro_appearance_prefs";
    public static final String KEY_UI_THEME = "selected_gboard_ui_theme";

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

            String selectedTheme = "default";
            try {
                SharedPreferences prefs = context.getSharedPreferences(PREF_APPEARANCE, Context.MODE_PRIVATE);
                selectedTheme = prefs.getString(KEY_UI_THEME, "default");
            } catch (Throwable ignored) {
            }

            // Material 3 Expressive Multi-Color Theme Overrides
            if ("rainbow".equals(selectedTheme)) {
                if (night) {
                    return new Palette(
                            0xFF1C1310, // windowBackground (Warm dark espresso)
                            0xFF281C17, // surface
                            0xFF372720, // surfaceAlt
                            0x44FF8A65, // surfaceStroke
                            0xFFFBE9E7, // textPrimary
                            0xFFFFCCBC, // textSecondary
                            0xFFFF7043, // accent (Vivid Sunset Coral)
                            0xFF5D2416, // accentContainer
                            0xFFFFDBCF, // onAccentContainer
                            0x33FF7043, // ripple
                            true
                    );
                } else {
                    return new Palette(
                            0xFFFFF8F6, // windowBackground (Warm light peach)
                            0xFFFDEEE9, // surface
                            0xFFF8E2DB, // surfaceAlt
                            0x26FF5722, // surfaceStroke
                            0xFF2D1610, // textPrimary
                            0xFF5F3E35, // textSecondary
                            0xFFE64A19, // accent (Vivid Expressive Coral)
                            0xFFFFCCBC, // accentContainer
                            0xFF3E0F04, // onAccentContainer
                            0x26E64A19, // ripple
                            false
                    );
                }
            } else if ("botanical".equals(selectedTheme)) {
                if (night) {
                    return new Palette(
                            0xFF0E1815, // windowBackground (Deep Sage Night)
                            0xFF162520, // surface
                            0xFF20342E, // surfaceAlt
                            0x4480CBC4, // surfaceStroke
                            0xFFE0F2F1, // textPrimary
                            0xFFB2DFDB, // textSecondary
                            0xFF4DB6AC, // accent (Fresh Mint Teal)
                            0xFF0E3E37, // accentContainer
                            0xFFB2DFDB, // onAccentContainer
                            0x334DB6AC, // ripple
                            true
                    );
                } else {
                    return new Palette(
                            0xFFF4FBF7, // windowBackground (Fresh Spring Light)
                            0xFFE8F5E9, // surface (Soft Botanical Sage)
                            0xFFDCEDC8, // surfaceAlt (Pastel Peach/Sage)
                            0x2600796B, // surfaceStroke
                            0xFF0A261F, // textPrimary
                            0xFF2E5349, // textSecondary
                            0xFF00796B, // accent (Botanical Mint Green)
                            0xFFC8E6C9, // accentContainer
                            0xFF00382F, // onAccentContainer
                            0x2600796B, // ripple
                            false
                    );
                }
            } else if ("ocean".equals(selectedTheme)) {
                if (night) {
                    return new Palette(
                            0xFF101322, // windowBackground (Twilight Oceanic)
                            0xFF191D32, // surface
                            0xFF232845, // surfaceAlt
                            0x449FA8DA, // surfaceStroke
                            0xFFE8EAF6, // textPrimary
                            0xFFC5CAE9, // textSecondary
                            0xFF7986CB, // accent (Lavender Twilight)
                            0xFF1E265C, // accentContainer
                            0xFFE8EAF6, // onAccentContainer
                            0x337986CB, // ripple
                            true
                    );
                } else {
                    return new Palette(
                            0xFFF6F8FE, // windowBackground (Ocean Breeze)
                            0xFFEEF2FC, // surface
                            0xFFE0E7FA, // surfaceAlt
                            0x263949AB, // surfaceStroke
                            0xFF12182E, // textPrimary
                            0xFF3F4A6A, // textSecondary
                            0xFF3949AB, // accent (Coastal Azure Indigo)
                            0xFFD1C4E9, // accentContainer (Soft Rose Lavender)
                            0xFF101642, // onAccentContainer
                            0x263949AB, // ripple
                            false
                    );
                }
            }

            // Default Dynamic Palette (Monet on Android 12+)
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

    public static int resolveActivityTheme(Activity activity) {
        try {
            android.content.pm.ActivityInfo info = activity.getPackageManager().getActivityInfo(
                    new android.content.ComponentName(activity, "com.google.android.apps.inputmethod.latin.preference.SettingsActivity"),
                    0);
            if (info.theme != 0) {
                return info.theme;
            }
        } catch (Throwable ignored) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return android.R.style.Theme_DeviceDefault_DayNight;
        }
        boolean nightMode = (activity.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        return nightMode
                ? android.R.style.Theme_DeviceDefault_NoActionBar
                : android.R.style.Theme_DeviceDefault_Light_NoActionBar;
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

    public static void enforceHighRefreshRate(Window window) {
        if (window == null) return;
        try {
            WindowManager.LayoutParams lp = window.getAttributes();
            float highestRate = 60.0f;
            int bestModeId = 0;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Display display = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    try {
                        display = window.getContext().getDisplay();
                    } catch (Throwable ignored) {
                    }
                }
                if (display == null) {
                    WindowManager wm = (WindowManager) window.getContext().getSystemService(Context.WINDOW_SERVICE);
                    if (wm != null) {
                        display = wm.getDefaultDisplay();
                    }
                }
                if (display != null) {
                    Display.Mode[] modes = display.getSupportedModes();
                    if (modes != null) {
                        for (Display.Mode mode : modes) {
                            float rate = mode.getRefreshRate();
                            if (rate > highestRate) {
                                highestRate = rate;
                                bestModeId = mode.getModeId();
                            }
                        }
                    }
                }
            }

            // Enforce max refresh rate (144Hz for tablets like Redmi Pad 7 Pro, 120Hz, 90Hz)
            if (highestRate >= 90.0f) {
                lp.preferredRefreshRate = highestRate;
                if (bestModeId != 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    lp.preferredDisplayModeId = bestModeId;
                }
            } else {
                // If display API didn't report, request 144Hz maximum
                lp.preferredRefreshRate = 144.0f;
            }

            if (Build.VERSION.SDK_INT >= 31) {
                try {
                    window.getClass().getMethod("setFrameRateSelectionPriority", int.class).invoke(window, 100);
                } catch (Throwable ignored) {
                }
            }

            window.setAttributes(lp);
        } catch (Throwable ignored) {
        }
    }

    public static void initActivity(final Activity activity, Palette palette) {
        if (activity == null || palette == null) return;

        // Hide action bar safely
        try {
            if (activity.getActionBar() != null) {
                activity.getActionBar().hide();
            }
        } catch (Throwable ignored) {
        }

        // Configure window, system bars, and refresh rate safely
        try {
            Window window = activity.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(palette.windowBackground));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                        window.setStatusBarColor(palette.windowBackground);
                        window.setNavigationBarColor(palette.windowBackground);
                    } catch (Throwable ignored) {
                    }
                }

                enforceHighRefreshRate(window);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    try {
                        window.setDecorFitsSystemWindows(true);
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
                    } catch (Throwable ignored) {
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    try {
                        View decor = window.getDecorView();
                        if (decor != null) {
                            int flags = decor.getSystemUiVisibility();
                            if (!palette.isNight) {
                                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !palette.isNight) {
                                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                            }
                            decor.setSystemUiVisibility(flags);
                        }
                    } catch (Throwable ignored) {
                    }
                }
            }
        } catch (Throwable ignored) {
        }

        // Apply smooth transition animations
        applyActivityEnterTransition(activity);

        // Register Android 13+ Predictive Back callback
        registerPredictiveBackCallback(activity);
    }

    public static void setupSystemBars(Activity activity, Palette palette) {
        initActivity(activity, palette);
    }

    public static void registerPredictiveBackCallback(final Activity activity) {
        if (activity == null || Build.VERSION.SDK_INT < 33) return;
        try {
            Class<?> callbackClass = Class.forName("android.window.OnBackInvokedCallback");
            Class<?> dispatcherClass = Class.forName("android.window.OnBackInvokedDispatcher");

            Object callback = Proxy.newProxyInstance(
                    callbackClass.getClassLoader(),
                    new Class<?>[]{callbackClass},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) {
                            if ("onBackInvoked".equals(method.getName())) {
                                activity.finish();
                                applyActivityExitTransition(activity);
                            }
                            return null;
                        }
                    }
            );

            Object dispatcher = activity.getClass().getMethod("getOnBackInvokedDispatcher").invoke(activity);
            if (dispatcher != null) {
                dispatcherClass.getMethod("registerOnBackInvokedCallback", int.class, callbackClass)
                        .invoke(dispatcher, 0, callback);
            }
        } catch (Throwable ignored) {
        }
    }

    public static Drawable createCardDrawable(int fillColor, int strokeColor, int cornerRadiusPx) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(cornerRadiusPx);
        shape.setColor(fillColor);
        if (strokeColor != Color.TRANSPARENT) {
            shape.setStroke(1, strokeColor);
        }
        return shape;
    }

    public static Drawable createClickableCardDrawable(int fillColor, int strokeColor, int rippleColor, int cornerRadiusPx) {
        Drawable content = createCardDrawable(fillColor, strokeColor, cornerRadiusPx);
        Drawable mask = createCardDrawable(Color.WHITE, Color.TRANSPARENT, cornerRadiusPx);
        return new RippleDrawable(ColorStateList.valueOf(rippleColor), content, mask);
    }

    public static View createToolbar(final Activity activity, String titleText, final Palette palette) {
        LinearLayout toolbar = new LinearLayout(activity);
        toolbar.setOrientation(LinearLayout.HORIZONTAL);
        toolbar.setGravity(Gravity.CENTER_VERTICAL);
        toolbar.setPadding(dp(activity, 8), dp(activity, 12), dp(activity, 16), dp(activity, 12));
        toolbar.setBackgroundColor(palette.windowBackground);

        ImageView backBtn = new ImageView(activity);
        backBtn.setPadding(dp(activity, 12), dp(activity, 12), dp(activity, 12), dp(activity, 12));
        backBtn.setImageDrawable(createBackArrowDrawable(activity, palette.textPrimary));
        backBtn.setBackground(createClickableCardDrawable(
                Color.TRANSPARENT, Color.TRANSPARENT, palette.ripple, dp(activity, 24)));
        backBtn.setClickable(true);
        backBtn.setFocusable(true);
        backBtn.setContentDescription("Back");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        int btnSize = dp(activity, 48);
        toolbar.addView(backBtn, new LinearLayout.LayoutParams(btnSize, btnSize));

        TextView title = new TextView(activity);
        title.setText(titleText);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(palette.textPrimary);
        title.setPadding(dp(activity, 12), 0, 0, 0);

        toolbar.addView(title, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        return toolbar;
    }

    private static Drawable createBackArrowDrawable(Context context, int color) {
        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.OVAL);
        d.setColor(color);
        return d;
    }

    public static void applyActivityEnterTransition(Activity activity) {
        if (activity == null) return;
        try {
            int enterAnim = activity.getResources().getIdentifier("m3_slide_in_right", "anim", activity.getPackageName());
            int exitAnim = activity.getResources().getIdentifier("m3_slide_out_left", "anim", activity.getPackageName());
            if (enterAnim != 0 && exitAnim != 0) {
                if (Build.VERSION.SDK_INT >= 34) {
                    try {
                        activity.overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, enterAnim, exitAnim);
                    } catch (Throwable t) {
                        activity.overridePendingTransition(enterAnim, exitAnim);
                    }
                } else {
                    activity.overridePendingTransition(enterAnim, exitAnim);
                }
            }
        } catch (Throwable ignored) {
        }
    }

    public static void applyActivityExitTransition(Activity activity) {
        if (activity == null) return;
        try {
            int enterAnim = activity.getResources().getIdentifier("m3_slide_in_left", "anim", activity.getPackageName());
            int exitAnim = activity.getResources().getIdentifier("m3_slide_out_right", "anim", activity.getPackageName());
            if (enterAnim != 0 && exitAnim != 0) {
                if (Build.VERSION.SDK_INT >= 34) {
                    try {
                        activity.overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, enterAnim, exitAnim);
                    } catch (Throwable t) {
                        activity.overridePendingTransition(enterAnim, exitAnim);
                    }
                } else {
                    activity.overridePendingTransition(enterAnim, exitAnim);
                }
            }
        } catch (Throwable ignored) {
        }
    }
}
