# 🚀 Gboard_Pro — Release Notes (`v18.4.1-V7` / Bundle `v1.2.0`)

## Overview
**Gboard_Pro `v18.4.1-V7`** (powered by Patch Bundle `v1.2.0`) delivers improved TalkBack accessibility in the features panel, fused APK dictation library verification, automatic foreground screenshot & clipboard syncing, American Sign Language (Sign-to-Text) camera recognition, and smarter auto-correction behavior built on **Google Gboard `18.4.1.985164140-release-arm64-v8a`**.

---

## ✨ Key Changes in `v18.4.1-V7`

### 1. Accessibility & TalkBack Focus Improvements
- **Window State Notifications**: Opening the keyboard tools panel (`AccessPointsPanel`, `AccessPointsPanelV2`, `AccessPointsPanelViewPage`) now properly dispatches `AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED` with a resolved pane title.
- **Automatic Focus Placement**: Screen readers immediately land on the first interactive tool button instead of losing focus.

### 2. Fused APK Dictation Readiness & Offline Voice Packs
- **Direct Native Library Check**: Uses `NativeLibHelper.c("dictation_jni", false)` so fused standalone APKs pass dictation readiness checks without requiring Play Store split-APK metadata.
- **Offline Model Downloads**: Fixes URL resolution for on-device voice recognition language packs (including `zh-TW`).

### 3. Instant Clipboard & Screenshot Synchronization
- **Foreground Observer Sync**: Invokes `ensureScreenshotObserver(handler)` during `onStartInputView` so recent screenshots and copied text appear right away.
- **Default XML Preferences**: Pre-enables `enable_screenshot_in_clipboard`, `pref_key_enable_clipboard`, and `pref_key_enable_emoji_suggestion`.

### 4. Sign-to-Text (ASL Camera Translation)
- **Signboard Integration**: Activates `enable_signboard`, `signboard_prompt_manual_trigger`, `on_demand_feature_split`, and `use_4c_in_signboard` for real-time American Sign Language translation on supported devices.

### 5. Refined Auto-Correction & Undo Behavior
- **Backspace / Primary-Key Revert**: Enables `auto_correct_with_replace_text = true` and `enable_revert_auto_correct_on_pk = true` to quickly undo unwanted auto-corrections.
- **Smarter Word Learning**: Sets `intended_oov_min_count = 2L` so single-occurrence typos aren't saved into your personal dictionary prematurely.

---

## 📦 Downloads

| File | Type | Size | Notes |
| :--- | :--- | :--- | :--- |
| [**Gboard_Pro-18.4.1-V7.apk**](https://github.com/smartworldarafath/Gboard_Pro/releases/download/v18.4.1-Stable-V7/Gboard_Pro-18.4.1-V7.apk) | **Signed APK** *(Recommended)* | ~127 MB | Ready to install alongside stock Gboard without root. |
| [**patches-1.2.0.mpp**](https://github.com/smartworldarafath/Gboard_Pro/releases/download/v18.4.1-Stable-V7/patches-1.2.0.mpp) | **Morphe Patch Bundle** | ~1.6 MB | Use with Morphe Manager to patch a clean Gboard APK. |
| [**Stock Gboard v18.4.1**](https://www.apkmirror.com/apk/google-inc/gboard/gboard-the-google-keyboard-18-4-1-985164140-release/) | **Base APK** | ~67 MB | Clean upstream APK from APKMirror (for manual patching). |

---

## 📲 Quick Setup

### Direct APK Install
1. Download [**Gboard_Pro-18.4.1-V7.apk**](https://github.com/smartworldarafath/Gboard_Pro/releases/download/v18.4.1-Stable-V7/Gboard_Pro-18.4.1-V7.apk) and install it on your device.
2. Enable **Gboard_Pro** in **Settings > System > Languages & input > On-screen keyboard**.

### Morphe Manager Repository URL
```text
https://raw.githubusercontent.com/smartworldarafath/Gboard_Pro/main/patches-bundle.json
```
