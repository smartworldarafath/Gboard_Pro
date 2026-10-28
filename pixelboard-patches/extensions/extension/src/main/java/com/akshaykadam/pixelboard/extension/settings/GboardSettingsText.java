package com.akshaykadam.pixelboard.extension.settings;

import android.content.Context;
import java.util.Locale;

public final class GboardSettingsText {
    private GboardSettingsText() {
    }

    public static String get(Context context, int resId) {
        if (context == null) return "";
        try {
            return context.getString(resId);
        } catch (Throwable t) {
            return "";
        }
    }

    public static String format(Context context, int resId, Object... args) {
        if (context == null) return "";
        try {
            return context.getString(resId, args);
        } catch (Throwable t) {
            return "";
        }
    }

    public static String quantity(Context context, int resId, int quantity) {
        if (context == null) return "";
        try {
            return context.getResources().getQuantityString(resId, quantity, quantity);
        } catch (Throwable t) {
            return "";
        }
    }
}
