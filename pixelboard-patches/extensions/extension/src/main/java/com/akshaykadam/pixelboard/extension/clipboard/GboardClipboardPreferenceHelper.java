package com.akshaykadam.pixelboard.extension.clipboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Method;

public final class GboardClipboardPreferenceHelper {

    private static final String TAG = "GboardClipboardHelper";
    public static final String KEY_STANDARD_RETENTION = "clipboard_history_retention";
    public static final String KEY_HOURLY_RETENTION = "clipboard_hourly_retention";
    public static final String KEY_ACTIVE_MODE = "active_clipboard_slider_mode";
    public static final String MODE_STANDARD = "standard";
    public static final String MODE_HOURLY = "hourly";

    private GboardClipboardPreferenceHelper() {
    }

    public static void onSliderChanged(Object preferenceObj, String selectedValue) {
        if (preferenceObj == null || selectedValue == null) return;
        try {
            String key = getPreferenceKey(preferenceObj);
            Context context = getPreferenceContext(preferenceObj);
            if (key == null || context == null) return;

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

            if (KEY_STANDARD_RETENTION.equals(key)) {
                // User moved the Standard slider
                prefs.edit()
                        .putString(KEY_ACTIVE_MODE, MODE_STANDARD)
                        .putString(KEY_STANDARD_RETENTION, selectedValue)
                        .apply();

                // Turn ON standard, turn OFF hourly
                setPreferenceEnabled(preferenceObj, true);
                setPreferenceSummary(preferenceObj, "Active: " + formatStandardValue(selectedValue));

                Object hourlyPref = findSiblingPreference(preferenceObj, KEY_HOURLY_RETENTION);
                if (hourlyPref != null) {
                    setPreferenceEnabled(hourlyPref, false);
                    setPreferenceSummary(hourlyPref, "Off (Using standard retention)");
                }
            } else if (KEY_HOURLY_RETENTION.equals(key)) {
                // User moved the Hourly slider
                prefs.edit()
                        .putString(KEY_ACTIVE_MODE, MODE_HOURLY)
                        .putString(KEY_HOURLY_RETENTION, selectedValue)
                        .putString(KEY_STANDARD_RETENTION, selectedValue) // Keep Gboard retention synced
                        .apply();

                // Turn ON hourly, turn OFF standard
                setPreferenceEnabled(preferenceObj, true);
                setPreferenceSummary(preferenceObj, "Active: " + selectedValue + (selectedValue.equals("1") ? " hour" : " hours"));

                Object standardPref = findSiblingPreference(preferenceObj, KEY_STANDARD_RETENTION);
                if (standardPref != null) {
                    setPreferenceEnabled(standardPref, false);
                    setPreferenceSummary(standardPref, "Off (Using hourly retention)");
                }
            }
        } catch (Throwable t) {
            Log.w(TAG, "Error in onSliderChanged", t);
        }
    }

    public static void onBindPreferenceView(final Object preferenceObj, final View itemView) {
        if (preferenceObj == null || itemView == null) return;
        try {
            final String key = getPreferenceKey(preferenceObj);
            final Context context = getPreferenceContext(preferenceObj);
            if (key == null || context == null) return;

            if (!KEY_STANDARD_RETENTION.equals(key) && !KEY_HOURLY_RETENTION.equals(key)) {
                return;
            }

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String activeMode = prefs.getString(KEY_ACTIVE_MODE, MODE_STANDARD);

            boolean isCurrentActive = (KEY_STANDARD_RETENTION.equals(key) && MODE_STANDARD.equals(activeMode))
                    || (KEY_HOURLY_RETENTION.equals(key) && MODE_HOURLY.equals(activeMode));

            setPreferenceEnabled(preferenceObj, isCurrentActive);

            if (isCurrentActive) {
                itemView.setAlpha(1.0f);
                if (KEY_STANDARD_RETENTION.equals(key)) {
                    String val = prefs.getString(KEY_STANDARD_RETENTION, "1");
                    setPreferenceSummary(preferenceObj, "Active: " + formatStandardValue(val));
                } else {
                    String val = prefs.getString(KEY_HOURLY_RETENTION, "1");
                    setPreferenceSummary(preferenceObj, "Active: " + val + (val.equals("1") ? " hour" : " hours"));
                }
            } else {
                itemView.setAlpha(0.55f);
                setPreferenceSummary(preferenceObj, "Off (Tap to activate this retention option)");

                // Make tapping the disabled preference instantly enable it and disable the other
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activateSlider(preferenceObj, key, context);
                    }
                });
            }
        } catch (Throwable t) {
            Log.w(TAG, "Error in onBindPreferenceView", t);
        }
    }

    private static void activateSlider(Object preferenceObj, String key, Context context) {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            if (KEY_STANDARD_RETENTION.equals(key)) {
                prefs.edit().putString(KEY_ACTIVE_MODE, MODE_STANDARD).apply();
                setPreferenceEnabled(preferenceObj, true);

                Object hourlyPref = findSiblingPreference(preferenceObj, KEY_HOURLY_RETENTION);
                if (hourlyPref != null) {
                    setPreferenceEnabled(hourlyPref, false);
                    setPreferenceSummary(hourlyPref, "Off (Using standard retention)");
                }
                String val = prefs.getString(KEY_STANDARD_RETENTION, "1");
                setPreferenceSummary(preferenceObj, "Active: " + formatStandardValue(val));
                Toast.makeText(context, "Activated Standard Retention", Toast.LENGTH_SHORT).show();
            } else if (KEY_HOURLY_RETENTION.equals(key)) {
                prefs.edit().putString(KEY_ACTIVE_MODE, MODE_HOURLY).apply();
                setPreferenceEnabled(preferenceObj, true);

                Object standardPref = findSiblingPreference(preferenceObj, KEY_STANDARD_RETENTION);
                if (standardPref != null) {
                    setPreferenceEnabled(standardPref, false);
                    setPreferenceSummary(standardPref, "Off (Using hourly retention)");
                }
                String val = prefs.getString(KEY_HOURLY_RETENTION, "1");
                prefs.edit().putString(KEY_STANDARD_RETENTION, val).apply();
                setPreferenceSummary(preferenceObj, "Active: " + val + (val.equals("1") ? " hour" : " hours"));
                Toast.makeText(context, "Activated Hourly Retention", Toast.LENGTH_SHORT).show();
            }
        } catch (Throwable t) {
            Log.w(TAG, "Error activating slider", t);
        }
    }

    private static String formatStandardValue(String val) {
        if ("1".equals(val)) return "1 hour";
        if ("24".equals(val)) return "24 hours";
        if ("168".equals(val)) return "7 days";
        if ("720".equals(val)) return "30 days";
        if ("never".equalsIgnoreCase(val) || "0".equals(val) || "87600".equals(val)) return "Never (Unlimited)";
        return val + " hours";
    }

    private static String getPreferenceKey(Object pref) {
        try {
            Method m = pref.getClass().getMethod("getKey");
            return (String) m.invoke(pref);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static Context getPreferenceContext(Object pref) {
        try {
            Method m = pref.getClass().getMethod("getContext");
            return (Context) m.invoke(pref);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static void setPreferenceEnabled(Object pref, boolean enabled) {
        try {
            Method m = pref.getClass().getMethod("setEnabled", boolean.class);
            m.invoke(pref, enabled);
        } catch (Throwable ignored) {
        }
    }

    private static void setPreferenceSummary(Object pref, CharSequence summary) {
        try {
            Method m = pref.getClass().getMethod("setSummary", CharSequence.class);
            m.invoke(pref, summary);
        } catch (Throwable ignored) {
        }
    }

    private static Object findSiblingPreference(Object pref, String targetKey) {
        try {
            Method getMgr = pref.getClass().getMethod("getPreferenceManager");
            Object mgr = getMgr.invoke(pref);
            if (mgr != null) {
                Method findPref = mgr.getClass().getMethod("findPreference", CharSequence.class);
                return findPref.invoke(mgr, targetKey);
            }
        } catch (Throwable ignored) {
        }
        return null;
    }
}
