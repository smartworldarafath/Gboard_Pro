/**
 * Gboard_Pro - Gboard Enhancement Mod
 *
 * Maintained and customized by Arafath (@smartworldarafath)
 * Repository: https://github.com/smartworldarafath/Gboard_Pro
 * Licensed under the GNU General Public License v3.0 (GPLv3)
 */
package com.akshaykadam.pixelboard.extension.settings;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.akshaykadam.pixelboard.extension.keyboard.GboardAiVoiceSettingsGroupFeature;
import com.akshaykadam.pixelboard.extension.updater.PixelBoardUpdateFeature;

public final class GboardPatchesSettingsFeatureRegistry {
    private static final String TAG = "GboardPro";

    private GboardPatchesSettingsFeatureRegistry() {
    }

    public static List<GboardPatchesSettingsContract.Feature> features(Context context) {
        List<GboardPatchesSettingsContract.Feature> features =
                new ArrayList<GboardPatchesSettingsContract.Feature>();
        addIfAvailable(context, features, new GboardAppearanceFeature(context));
        addIfAvailable(context, features, new GboardBoardUiFeature(context));
        addIfAvailable(context, features, new GboardAiVoiceSettingsGroupFeature(context));
        addIfAvailable(context, features, new PixelBoardUpdateFeature(context));
        addIfAvailable(context, features, new GboardAboutFeature(context));
        return Collections.unmodifiableList(features);
    }

    private static void addIfAvailable(Context context,
            List<GboardPatchesSettingsContract.Feature> features,
            GboardPatchesSettingsContract.Feature feature) {
        try {
            if (feature.isAvailable(context)) {
                features.add(feature);
            }
        } catch (Throwable throwable) {
            Log.w(TAG, "Failed to evaluate feature availability: " + feature.getClass().getName(),
                    throwable);
        }
    }
}
