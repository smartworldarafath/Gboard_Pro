<div align="center">

# ⌨️ Gboard_Pro

**An Advanced, Privacy-Conscious Google Keyboard Mod Engineered with Next-Gen Voice Dictation, On-Device AI Writing Capabilities & Material 3 Expressive Design — Zero Root Required.**

<p>
  <a href="https://github.com/smartworldarafath/Gboard_Pro/releases/tag/v18.4.1-Stable-V7">
    <img src="https://img.shields.io/badge/Version-v18.4.1--V7-6C5CE7?style=flat-square&logo=github&logoColor=white" alt="Latest Build"/>
  </a>
  <img src="https://img.shields.io/badge/Platform-Android%2010%E2%80%9316-00B894?style=flat-square&logo=android&logoColor=white" alt="Android Compatibility"/>
  <img src="https://img.shields.io/badge/ABI-arm64--v8a-0984E3?style=flat-square" alt="CPU Architecture"/>
  <img src="https://img.shields.io/badge/Upstream-Gboard%2018.4.1-E17055?style=flat-square&logo=google" alt="Gboard Base"/>
  <img src="https://img.shields.io/badge/Root%20Access-Not%20Needed-2ECC71?style=flat-square" alt="Non-Root"/>
  <img src="https://img.shields.io/badge/License-GPLv3-636E72?style=flat-square" alt="Open Source License"/>
</p>

<p>
  <a href="https://github.com/smartworldarafath/Gboard_Pro/releases/download/1.2.0/Gboard_Pro-18.4.1-V7.apk">
    <img src="https://img.shields.io/badge/⬇️_Get_Signed_APK-v18.4.1_V7_(127_MB)-00C853?style=for-the-badge&logo=android&logoColor=white" height="42" alt="Download Gboard_Pro APK"/>
  </a>
  &nbsp;&nbsp;
  <a href="https://github.com/smartworldarafath/Gboard_Pro/releases/download/1.2.0/patches-1.2.0.mpp">
    <img src="https://img.shields.io/badge/🧩_Patch_Bundle_(.mpp)-v1.2.0_(1.6_MB)-2979FF?style=for-the-badge&logo=android&logoColor=white" height="42" alt="Download Morphe Patch Bundle"/>
  </a>
</p>

<p>
  <sub><b>Quick Start:</b> Grab the pre-signed <b>APK</b> to install alongside your default keyboard without root, or pick the <b>.mpp bundle</b> to patch a clean base APK yourself.</sub>
</p>

<p>
  <a href="https://github.com/smartworldarafath/Gboard_Pro/releases/tag/v18.4.1-Stable-V7"><b>Changelog</b></a> &nbsp;&bull;&nbsp;
  <a href="#setup--installation"><b>Setup Instructions</b></a> &nbsp;&bull;&nbsp;
  <a href="#interface-preview"><b>UI Preview</b></a> &nbsp;&bull;&nbsp;
  <a href="https://github.com/smartworldarafath/Gboard_Pro/releases"><b>All Builds</b></a>
</p>

</div>

---

## 📦 Available Downloads (`v18.4.1-V7` • Patch `v1.2.0`)

| Artifact | Format | Summary |
| :--- | :--- | :--- |
| [**Gboard_Pro-18.4.1-V7.apk**](https://github.com/smartworldarafath/Gboard_Pro/releases/download/v18.4.1-Stable-V7/Gboard_Pro-18.4.1-V7.apk) | **Pre-built APK** *(Recommended)* | **Plug-and-play installation.** Runs independently alongside stock Gboard with zero conflicts (`com.akshaykadam.pixelboard`). |
| [**patches-1.2.0.mpp**](https://github.com/smartworldarafath/Gboard_Pro/releases/download/v18.4.1-Stable-V7/patches-1.2.0.mpp) | **Morphe Patch Pack** | Modular patch archive for [Morphe Manager](#method-3-patch-on-device-with-morphe-manager) if you prefer compiling your own APK. |
| [**Official Gboard v18.4.1**](https://www.apkmirror.com/apk/google-inc/gboard/gboard-the-google-keyboard-18-4-1-985164140-release/) | **Unmodified Base** | Clean upstream package from APKMirror *(needed only when patching manually)*. |

---

## 🖼️ Interface Preview

<div align="center">
  <img src="docs/images/showcase.png" alt="Gboard_Pro Interface Overview - Rambler Dictation, AI Writing Suite & Custom Settings" width="100%" />
</div>

---

## 🚀 Why Gboard_Pro?

**Gboard_Pro** refines Google's standard keyboard into a fast, feature-unlocked typing environment. Instead of cluttering the experience with telemetry prompts or locked regional flags, this project activates Google's flagship typing technologies out of the box: **Gemini Rambler Conversational Dictation**, **Integrated AI Proofreading & Tone Rewriting**, **Inline Android 12+ Spell Check Chips**, **Camera Text Scanner (OCR)**, **Instant Screenshot Paste Banners**, **Material 3 Belka Pill Keycaps**, **Redesigned Emoji Palette V2**, **Super Insert Media Hub**, and **One UI / Vivo Clipboard Persistence**.

Because **Gboard_Pro** uses a dedicated application namespace, it coexists peacefully with your phone's pre-installed Gboard—no root privileges, system partition edits, or uninstalls required.

---

## ✨ Highlighted Capabilities

### 1. Conversational Voice Dictation ("Rambler" Engine)
Standard speech recognition writes down every pause and slip of the tongue. **Gboard_Pro** enables Google's next-generation **Rambler** dictation pipeline:
- **Automatic Filler Removal**: Speak at your normal pace—hesitations such as *"uh"*, *"um"*, and *"like"* are stripped out on the fly.
- **Mid-Sentence Self-Correction**: Change your mind while speaking (e.g., *"send it on Monday wait Tuesday"*) and watch the transcript resolve cleanly to your final intent.
- **Smart Punctuation & Casing**: Inserts periods, commas, question marks, and proper capitalization automatically from vocal context.
- **Seamless Code-Switching**: Dictate across multiple languages within the same phrase without manually switching layouts.
- **Pre-Configured for v18.4.1**: Directly wired into Gboard 18.4.1's `VoiceSettingsFragment` with `enable_jetson = true` initialized by default, removing any dependency on Google Play Phenotype server rollouts.
- **Zero Idle Battery Drain**: Releases `GoogleAsrService` bindings the moment the keyboard closes so background speech services never drain battery while your screen is off.
- **Direct Boot Ready**: Stores configuration in Device-Protected Storage so voice dictation works immediately after a reboot.

### 2. Built-In AI Writing Suite
Bring Gemini-powered text refinement to any input field on your phone:
- **Instant Proofreading**: Catch grammar mistakes, typos, and awkward phrasing with a single tap.
- **Adaptive Tone Shifting**: Rewrite drafts into *Professional*, *Casual*, *Concise*, or *Expressive* styles.
- **Android 16 Stability Guards**: Includes null-safe span handling to prevent crashes when expanding quick-insert suggestions on Android 16.
- **Language-Agnostic**: Unlocked across all keyboard layouts rather than being restricted to English-only regions.

### 3. Inline Spell Checker Chips & Grammar Engine
- **Modern Suggestion Chips (`GboardSpellCheckerChipsPatch`)**: Patches Gboard's internal spell-check renderer (`rcq.m` & `rcq.o`) to display modern Android 12+ inline replacement chips and quick dictionary actions on every device.
- **Pre-Seeded Accuracy Flags**: Enables `spellchecker_enable_chips_ui`, `enable_mark_misspelled_words`, `pref_key_enable_grammar_checker`, and `show_suggestion_strip` automatically on first launch.
- **Dedicated System Service Label**: Registers in Android system settings under **Gboard_Pro Spell Checker** so you can distinguish it from the stock service.

### 4. Camera OCR ("Scan Text") & Floating Screenshot Chips
- **Live Camera Text Extraction**: Unlocks the native CameraX "Scan text" viewfinder (`enable_ocr`, `config_ocr`, `enabled_ocr_language_tags = *`) with required camera permissions to capture text from the real world straight into your cursor position.
- **One-Tap Screenshot Paste**: Activates `enable_clipboard_image_suggestion`, `enable_screenshot_notification_flow`, and `enable_screenshot_chip`. Whenever you take a screenshot, a floating preview chip offers instant insertion.

### 5. Material 3 Expressive Pill Keycaps (Belka UI)
- **Belka Keycap Geometry**: Switch between classic rectangular keys and Google's Material 3 rounded pill keycaps from Advanced Settings.
- **Runtime Flag Sync**: Manages `belka_rounded_keyboard`, `belka_ui`, `belka_background`, `more_pill_keys`, and `enable_rounded_keys` dynamically.

### 6. Emoji Palette V2 & Restored Emoji Scaling
- **Separated Recent & Frequent Views**: Unlocks `emoji_palette_v2` along with dedicated tabs for recently used and frequently used emojis.
- **Emoji Size Slider & Full Opacity**: Restores the hidden **Emoji size** slider under *Emojis, stickers & GIFs* and forces 100% alpha (`255`) on suggestion-strip emoji candidates so icons always look crisp and vibrant.

### 7. Super Insert Hub & Cross-App Smart Compose
- **Unified Super Insert Panel**: Combines clipboard entries, emojis, contacts, and quick phrases into a single Material 3 bottom sheet (`super_insert`, `super_insert_vk`, `super_insert_enable_dynamic_multi_view`).
- **In-Line Ghost Autocomplete**: Brings Gmail-style predictive ghost text (`enable_smart_compose`, `enable_inline_suggestions_on_all_fields`) to standard text fields across third-party apps.

### 8. OEM Clipboard Fix & Fast Vocabulary Learning
- **Samsung One UI & Vivo Clipboard Sync**: Re-binds the clipboard observer on `onStartInputView` so copied text and screenshots always appear in your clipboard history, bypassing aggressive OEM background restrictions.
- **Rapid Personal Dictionary & Primary-Key Revert**: Tunes Delight5 N-gram scoring (`intended_oov_min_count = 2`, `enable_revert_auto_correct_on_pk = true`) for smarter word learning, 2-letter prefix completion, and effortless backspace undo on auto-corrected words.
- **Sign-to-Text (ASL Camera Recognition)**: Enables Gboard's experimental Signboard flags (`enable_signboard`, `use_4c_in_signboard`) for American Sign Language gesture translation on supported hardware.

### 9. Real-Time Toggles & Clean Material You Preferences
- **Live Configuration (No Force-Stop Needed)**: Switching voice engines, toggling AI tools, or changing keycap styles takes effect immediately without killing the keyboard process.
- **Localized in 20+ Languages**: Built-in translations for English, Arabic, Spanish, French, German, Hindi, Japanese, Korean, Chinese, Russian, Portuguese, Italian, Turkish, Indonesian, Vietnamese, Polish, Dutch, Thai, and Ukrainian.
- **Zero Clutter**: Strips out "Rate Gboard" and promotional feedback entries while supporting inline password-manager autofill chips (Bitwarden, 1Password, KeePass, etc.).

> [!NOTE]
> **Hardware Note for On-Device AI**: Features that rely on local Gemini Nano weights—specifically **Writing Tools V2** and the **On-Device AICore / Astrea Backend Selector**—require compatible **Google Pixel hardware** (Pixel 8 Pro, Pixel 9 series, or newer). On non-Pixel phones, those hardware-specific menus stay out of your way while **Rambler Voice Dictation**, **Cloud AI Proofreading/Rewriting**, **Spell Check Chips**, and **OCR** work across all Android 10–16 devices.

---

## 🎙️ Enabling Rambler Voice Dictation

1. **Open Gboard_Pro Settings**:
   - Tap the **Settings (`⚙️`)** icon on the keyboard toolbar, or navigate to **Settings > System > Languages & input > On-screen keyboard > Gboard_Pro**.
2. **Choose the Rambler Engine**:
   - Open **Voice typing**.
   - Tap **Dictation type** and switch from *Standard* to **Rambler**.
3. **Start Dictating Immediately**:
   - Changes apply live—no app restart required.
   - Tap the **Microphone** button in any text field, accept the **"Just speak naturally"** prompt once, and dictate hands-free.

---

## ✍️ Using the AI Writing Assistant

1. **Verify Toggles**:
   - In **Gboard_Pro Settings**, open **★ Advanced settings** at the bottom.
   - Confirm **Enable AI Writing Tools** and **Support All Keyboards** are switched **ON** (enabled by default).
   - Under **Corrections & suggestions > Writing tools**, make sure the suggestion-strip shortcut is active.
2. **Proofread or Rewrite**:
   - Type a sentence in any app and tap the **Sparkle Pen (`✨`)** icon on the suggestion strip.
   - Pick **Proofread** to fix errors or choose a tone preset (*Professional*, *Casual*, *Concise*, *Emotive*) to rewrite your text.

---

## 📲 Setup & Installation

### Method 1: Direct APK Install (Fastest)
1. Download [**Gboard_Pro-18.4.1-V7.apk**](https://github.com/smartworldarafath/Gboard_Pro/releases/download/v18.4.1-Stable-V7/Gboard_Pro-18.4.1-V7.apk).
2. Open the file on your Android device and confirm installation.
3. Activate **Gboard_Pro** under **Settings > System > Languages & input > On-screen keyboard**.

### Method 2: Install via ADB
```bash
adb install -r Gboard_Pro-18.4.1-V7.apk
adb shell am force-stop com.akshaykadam.pixelboard
```

### Method 3: Patch On-Device with Morphe Manager
1. Open **Morphe Manager** on your phone.
2. Download the patch bundle [**Gboard_Pro.mpp**](https://github.com/smartworldarafath/Gboard_Pro/raw/main/patches/Gboard_Pro.mpp) and the unmodified [**Gboard v18.4.1 APK**](https://www.apkmirror.com/apk/google-inc/gboard/gboard-the-google-keyboard-18-4-1-985164140-release/).
3. Select the base APK from storage, load `Gboard_Pro.mpp`, and run the patcher.

> [!TIP]
> To receive automatic patch updates inside Morphe Manager, register this custom bundle URL:
> ```text
> https://raw.githubusercontent.com/smartworldarafath/Gboard_Pro/main/patches-bundle.json
> ```

---

## 🗂️ Repository Layout

```text
Gboard_Pro/
├── README.md                  # Documentation & setup instructions
├── build.sh                   # Automated offline build & patching runner
├── options.json               # Default patch options (app_display_name = Gboard_Pro)
├── patches/
│   ├── Gboard_Pro.mpp         # Pre-compiled Morphe patch bundle
│   └── patches-bundle.json    # Remote update manifest for Morphe Manager
├── input/
│   └── gboard.apk             # Clean upstream Gboard v18.4.1 APK
├── output/
│   ├── Gboard_Pro.apk         # Signed output APK
│   └── patching-result.json   # Patch execution summary
├── tools/
│   ├── patcher.jar            # CLI patcher (Java 21+)
│   └── patcher-data/          # Local signing keystore & work dir
└── pixelboard-patches/        # Kotlin bytecode patches & Android extension module
    ├── patches/               # Morphe bytecode/resource patch definitions
    └── extensions/            # Injected runtime classes & Material You settings UI
```

---

## 🛠️ Compiling from Source

### 1. Offline APK Patching
Ensure `input/gboard.apk` is present, then execute:
```bash
./build.sh
```
This applies `patches/Gboard_Pro.mpp` using `tools/patcher.jar` and signs `output/Gboard_Pro.apk`.

### 2. Recompiling the `.mpp` Patch Bundle
After editing Kotlin or Java files inside `pixelboard-patches/`:
```bash
./build.sh --rebuild
```

### 3. Executing Contract & Unit Tests
```bash
cd pixelboard-patches
./gradlew test
```

---

## 📋 Technical Specifications

| Attribute | Value |
| :--- | :--- |
| **Upstream Package** | `com.google.android.inputmethod.latin` |
| **Coexistence Package** | `com.akshaykadam.pixelboard` (App Label: `Gboard_Pro`) |
| **Primary Target** | `18.4.1.985164140-release-arm64-v8a` |
| **Additional Targets** | `18.3.1.977415014-release-arm64-v8a` / `18.3.1-beta` |
| **Supported ABI** | `arm64-v8a` |
| **Android OS Range** | Android 10 (API 29) – Android 16 |
| **Root Requirement** | None |

---

## 👤 Maintainer

**Arafath**
- GitHub: [@smartworldarafath](https://github.com/smartworldarafath)
- Project Link: [Gboard_Pro](https://github.com/smartworldarafath/Gboard_Pro)

---

## ⚖️ Disclaimer

> [!CAUTION]
> **READ BEFORE INSTALLING OR COMPILING THIS PROJECT.**

1. **Independent Project**: **Gboard_Pro** is an independent open-source modification created for educational research and interoperability. It is not affiliated with, endorsed by, or connected to **Google LLC**.
2. **Trademarks**: "Google", "Gboard", "Pixel", "Android", and "Gemini" are trademarks of Google LLC. Any references to these names are strictly for descriptive compatibility purposes.
3. **No Warranty**: Provided **"AS IS"** under the GPLv3 license without warranties of any kind. Users assume full responsibility for installing modified packages on their devices.
