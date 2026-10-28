package com.akshaykadam.pixelboard.extension.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GboardAppUpdatesActivity extends Activity {

    private static final String GITHUB_API_URL = "https://api.github.com/repos/smartworldarafath/Gboard_Pro/releases/latest";
    private static final String CURRENT_VERSION = "1.0.2";

    private GboardM3Helper.Palette palette;
    private Handler mainHandler;

    private TextView statusTitle;
    private TextView statusDescription;
    private TextView versionBadge;
    private LinearLayout releaseNotesCard;
    private TextView releaseNotesText;
    private LinearLayout progressContainer;
    private ProgressBar downloadProgressBar;
    private TextView progressText;
    private LinearLayout actionButton;
    private TextView actionButtonLabel;

    private String latestVersionTag = "";
    private String latestReleaseName = "";
    private String latestReleaseBody = "";
    private String downloadApkUrl = "";
    private File downloadedApkFile = null;

    private boolean isChecking = false;
    private boolean isDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        palette = GboardM3Helper.Palette.forContext(this);
        mainHandler = new Handler(Looper.getMainLooper());
        GboardM3Helper.setupSystemBars(this, palette);
        GboardM3Helper.applyActivityEnterTransition(this);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(palette.windowBackground);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        root.addView(GboardM3Helper.createToolbar(this, "Gboard Pro Updates", palette));

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setVerticalScrollBarEnabled(false);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(GboardM3Helper.dp(this, 16), GboardM3Helper.dp(this, 8),
                GboardM3Helper.dp(this, 16), GboardM3Helper.dp(this, 28));

        // Version Info Card
        content.addView(buildCurrentVersionCard());

        // Status Card
        content.addView(buildStatusCard());

        // Release Notes Card (hidden initially)
        releaseNotesCard = (LinearLayout) buildReleaseNotesCard();
        content.addView(releaseNotesCard);

        // Progress Card (hidden initially)
        progressContainer = (LinearLayout) buildProgressCard();
        content.addView(progressContainer);

        // Action Button
        actionButton = (LinearLayout) buildActionButton();
        content.addView(actionButton);

        scrollView.addView(content);
        root.addView(scrollView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setContentView(root);

        // Automatically check for updates on open
        checkForUpdates();
    }

    private View buildCurrentVersionCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setBackground(GboardM3Helper.createCardDrawable(
                palette.surface, palette.surfaceStroke, GboardM3Helper.dp(this, 20)));
        card.setPadding(GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 16),
                GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 16));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = GboardM3Helper.dp(this, 12);
        card.setLayoutParams(lp);

        LinearLayout textCol = new LinearLayout(this);
        textCol.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(this);
        title.setText("Gboard Pro");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(palette.textPrimary);
        textCol.addView(title);

        TextView versionLabel = new TextView(this);
        versionLabel.setText("Installed Version: v" + CURRENT_VERSION);
        versionLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        versionLabel.setTextColor(palette.textSecondary);
        LinearLayout.LayoutParams vLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vLp.topMargin = GboardM3Helper.dp(this, 2);
        textCol.addView(versionLabel, vLp);

        card.addView(textCol, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        versionBadge = new TextView(this);
        versionBadge.setText("v" + CURRENT_VERSION);
        versionBadge.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
        versionBadge.setTypeface(Typeface.DEFAULT_BOLD);
        versionBadge.setTextColor(palette.onAccentContainer);
        versionBadge.setBackground(GboardM3Helper.createCardDrawable(
                palette.accentContainer, Color.TRANSPARENT, GboardM3Helper.dp(this, 12)));
        versionBadge.setPadding(GboardM3Helper.dp(this, 12), GboardM3Helper.dp(this, 6),
                GboardM3Helper.dp(this, 12), GboardM3Helper.dp(this, 6));
        card.addView(versionBadge);

        return card;
    }

    private View buildStatusCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackground(GboardM3Helper.createCardDrawable(
                palette.surface, palette.surfaceStroke, GboardM3Helper.dp(this, 20)));
        card.setPadding(GboardM3Helper.dp(this, 20), GboardM3Helper.dp(this, 20),
                GboardM3Helper.dp(this, 20), GboardM3Helper.dp(this, 20));

        statusTitle = new TextView(this);
        statusTitle.setText("Checking for updates...");
        statusTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.5f);
        statusTitle.setTypeface(Typeface.DEFAULT_BOLD);
        statusTitle.setTextColor(palette.textPrimary);
        card.addView(statusTitle);

        statusDescription = new TextView(this);
        statusDescription.setText("Connecting to official GitHub release channel...");
        statusDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        statusDescription.setTextColor(palette.textSecondary);
        statusDescription.setLineSpacing(GboardM3Helper.dp(this, 2), 1.15f);
        LinearLayout.LayoutParams dLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dLp.topMargin = GboardM3Helper.dp(this, 6);
        card.addView(statusDescription, dLp);

        return card;
    }

    private View buildReleaseNotesCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackground(GboardM3Helper.createCardDrawable(
                palette.surface, palette.surfaceStroke, GboardM3Helper.dp(this, 18)));
        card.setPadding(GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 16),
                GboardM3Helper.dp(this, 18), GboardM3Helper.dp(this, 16));
        card.setVisibility(View.GONE);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = GboardM3Helper.dp(this, 12);
        card.setLayoutParams(lp);

        TextView label = new TextView(this);
        label.setText("WHAT'S NEW IN THIS RELEASE");
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11.5f);
        label.setTypeface(Typeface.DEFAULT_BOLD);
        label.setTextColor(palette.accent);
        card.addView(label);

        releaseNotesText = new TextView(this);
        releaseNotesText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        releaseNotesText.setTextColor(palette.textSecondary);
        releaseNotesText.setLineSpacing(GboardM3Helper.dp(this, 2), 1.15f);
        LinearLayout.LayoutParams tLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tLp.topMargin = GboardM3Helper.dp(this, 6);
        card.addView(releaseNotesText, tLp);

        return card;
    }

    private View buildProgressCard() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackground(GboardM3Helper.createCardDrawable(
                palette.surfaceAlt, palette.surfaceStroke, GboardM3Helper.dp(this, 16)));
        card.setPadding(GboardM3Helper.dp(this, 16), GboardM3Helper.dp(this, 14),
                GboardM3Helper.dp(this, 16), GboardM3Helper.dp(this, 14));
        card.setVisibility(View.GONE);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = GboardM3Helper.dp(this, 12);
        card.setLayoutParams(lp);

        progressText = new TextView(this);
        progressText.setText("Downloading update package...");
        progressText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        progressText.setTextColor(palette.textPrimary);
        card.addView(progressText);

        downloadProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        downloadProgressBar.setIndeterminate(false);
        downloadProgressBar.setMax(100);
        downloadProgressBar.setProgress(0);
        LinearLayout.LayoutParams pbLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, GboardM3Helper.dp(this, 8));
        pbLp.topMargin = GboardM3Helper.dp(this, 8);
        card.addView(downloadProgressBar, pbLp);

        return card;
    }

    private View buildActionButton() {
        LinearLayout button = new LinearLayout(this);
        button.setOrientation(LinearLayout.HORIZONTAL);
        button.setGravity(Gravity.CENTER);
        button.setBackground(GboardM3Helper.createClickableCardDrawable(
                palette.accent, Color.TRANSPARENT, palette.ripple, GboardM3Helper.dp(this, 22)));
        button.setPadding(GboardM3Helper.dp(this, 20), GboardM3Helper.dp(this, 14),
                GboardM3Helper.dp(this, 20), GboardM3Helper.dp(this, 14));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = GboardM3Helper.dp(this, 18);
        button.setLayoutParams(lp);

        actionButtonLabel = new TextView(this);
        actionButtonLabel.setText("Check Again");
        actionButtonLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.5f);
        actionButtonLabel.setTypeface(Typeface.DEFAULT_BOLD);
        actionButtonLabel.setTextColor(palette.isNight ? 0xFF00325B : 0xFFFFFFFF);
        button.addView(actionButtonLabel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionButtonClicked();
            }
        });

        return button;
    }

    private void onActionButtonClicked() {
        if (isDownloading) {
            return;
        }

        // If APK already downloaded, trigger install
        if (downloadedApkFile != null && downloadedApkFile.exists()) {
            promptApkInstallation(downloadedApkFile);
            return;
        }

        // If new version available and we have download URL, download it
        if (!downloadApkUrl.isEmpty()) {
            startApkDownload(downloadApkUrl);
            return;
        }

        // Default: re-check
        checkForUpdates();
    }

    private void checkForUpdates() {
        if (isChecking || isDownloading) return;
        isChecking = true;

        statusTitle.setText("Checking for updates...");
        statusDescription.setText("Connecting to https://github.com/smartworldarafath/Gboard_Pro/releases...");
        actionButtonLabel.setText("Checking...");
        actionButton.setEnabled(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(GITHUB_API_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "GboardPro-Android");
                    conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(15000);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        reader.close();

                        JSONObject json = new JSONObject(sb.toString());
                        final String tag = json.optString("tag_name", "").replace("v", "").trim();
                        final String name = json.optString("name", "Gboard Pro Release");
                        final String body = json.optString("body", "");

                        String apkUrl = "";
                        JSONArray assets = json.optJSONArray("assets");
                        if (assets != null) {
                            for (int i = 0; i < assets.length(); i++) {
                                JSONObject asset = assets.getJSONObject(i);
                                String assetName = asset.optString("name", "");
                                if (assetName.endsWith(".apk")) {
                                    apkUrl = asset.optString("browser_download_url", "");
                                    break;
                                }
                            }
                        }
                        final String finalApkUrl = apkUrl;

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onUpdateCheckSuccess(tag, name, body, finalApkUrl);
                            }
                        });
                    } else {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onUpdateCheckError("Server responded with code " + responseCode);
                            }
                        });
                    }
                    conn.disconnect();
                } catch (final Throwable t) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onUpdateCheckError(t.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    private void onUpdateCheckSuccess(String tag, String name, String body, String apkUrl) {
        isChecking = false;
        actionButton.setEnabled(true);
        latestVersionTag = tag;
        latestReleaseName = name;
        latestReleaseBody = body;
        downloadApkUrl = apkUrl;

        boolean isNewer = compareVersions(latestVersionTag, CURRENT_VERSION) > 0;

        if (isNewer) {
            statusTitle.setText("New Update Available: v" + latestVersionTag);
            statusDescription.setText(name + "\nA newer release is ready for installation.");
            versionBadge.setText("v" + latestVersionTag);
            versionBadge.setTextColor(palette.accent);

            if (!body.trim().isEmpty()) {
                releaseNotesText.setText(body.trim());
                releaseNotesCard.setVisibility(View.VISIBLE);
            }

            actionButtonLabel.setText("Download & Install Update");
        } else {
            statusTitle.setText("Gboard Pro is Up to Date");
            statusDescription.setText("You are currently running the latest stable release (v" + CURRENT_VERSION + ").");
            versionBadge.setText("v" + CURRENT_VERSION + " (Latest)");
            releaseNotesCard.setVisibility(View.GONE);
            actionButtonLabel.setText("Check for Updates");
        }
    }

    private void onUpdateCheckError(String errorMsg) {
        isChecking = false;
        actionButton.setEnabled(true);
        statusTitle.setText("Update Check Completed");
        statusDescription.setText("Currently running v" + CURRENT_VERSION + ".\n" + (errorMsg != null ? errorMsg : "Check GitHub releases manually."));
        actionButtonLabel.setText("Check Again");
    }

    private int compareVersions(String v1, String v2) {
        try {
            String[] parts1 = v1.split("\\.");
            String[] parts2 = v2.split("\\.");
            int maxLen = Math.max(parts1.length, parts2.length);
            for (int i = 0; i < maxLen; i++) {
                int p1 = i < parts1.length ? Integer.parseInt(parts1[i].replaceAll("[^0-9]", "")) : 0;
                int p2 = i < parts2.length ? Integer.parseInt(parts2[i].replaceAll("[^0-9]", "")) : 0;
                if (p1 != p2) {
                    return Integer.compare(p1, p2);
                }
            }
        } catch (Throwable ignored) {
        }
        return 0;
    }

    private void startApkDownload(final String apkUrl) {
        if (isDownloading) return;
        isDownloading = true;

        progressContainer.setVisibility(View.VISIBLE);
        downloadProgressBar.setProgress(0);
        progressText.setText("Connecting to download server...");
        actionButtonLabel.setText("Downloading...");
        actionButton.setEnabled(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apkUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setInstanceFollowRedirects(true);
                    conn.setRequestProperty("User-Agent", "GboardPro-Android");
                    conn.connect();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_MOVED_PERM ||
                            responseCode == HttpURLConnection.HTTP_MOVED_TEMP ||
                            responseCode == 307 || responseCode == 308) {
                        String newUrl = conn.getHeaderField("Location");
                        conn.disconnect();
                        url = new URL(newUrl);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestProperty("User-Agent", "GboardPro-Android");
                        conn.connect();
                    }

                    final int totalBytes = conn.getContentLength();
                    File destDir = getExternalFilesDir(null);
                    if (destDir == null) {
                        destDir = getCacheDir();
                    }
                    final File targetApk = new File(destDir, "Gboard_Pro_Update.apk");
                    if (targetApk.exists()) {
                        targetApk.delete();
                    }

                    InputStream in = conn.getInputStream();
                    FileOutputStream out = new FileOutputStream(targetApk);
                    byte[] buffer = new byte[8192];
                    int read;
                    int downloaded = 0;

                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                        downloaded += read;
                        if (totalBytes > 0) {
                            final int percent = Math.round((downloaded * 100f) / totalBytes);
                            final int mbCurrent = downloaded / (1024 * 1024);
                            final int mbTotal = totalBytes / (1024 * 1024);
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    downloadProgressBar.setProgress(percent);
                                    progressText.setText("Downloading: " + percent + "% (" + mbCurrent + " MB / " + mbTotal + " MB)");
                                }
                            });
                        }
                    }

                    out.flush();
                    out.close();
                    in.close();
                    conn.disconnect();

                    downloadedApkFile = targetApk;
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onDownloadComplete(targetApk);
                        }
                    });
                } catch (final Throwable t) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onDownloadFailed(t.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    private void onDownloadComplete(File apkFile) {
        isDownloading = false;
        progressContainer.setVisibility(View.GONE);
        actionButton.setEnabled(true);
        actionButtonLabel.setText("Install Update");
        statusTitle.setText("Update Downloaded Successfully");
        statusDescription.setText("The installation package is ready. Tap 'Install Update' to complete setup.");

        // Automatically launch installation
        promptApkInstallation(apkFile);
    }

    private void onDownloadFailed(String error) {
        isDownloading = false;
        progressContainer.setVisibility(View.GONE);
        actionButton.setEnabled(true);
        actionButtonLabel.setText("Retry Download");
        statusTitle.setText("Download Interrupted");
        statusDescription.setText("Failed to download update: " + (error != null ? error : "Network error."));
    }

    private void promptApkInstallation(File apkFile) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri;
            String authority = getPackageName() + ".fileprovider";

            try {
                Class<?> fpClass = Class.forName("androidx.core.content.FileProvider");
                java.lang.reflect.Method getUriMethod = fpClass.getMethod("getUriForFile", Context.class, String.class, File.class);
                uri = (Uri) getUriMethod.invoke(null, this, authority, apkFile);
            } catch (Throwable ignored) {
                uri = Uri.fromFile(apkFile);
            }

            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Throwable t) {
            Toast.makeText(this, "Could not open installer: " + t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GboardM3Helper.applyActivityExitTransition(this);
    }
}
