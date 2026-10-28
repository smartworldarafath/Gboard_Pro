## Overview

Gboard Pro v1.0.2 delivers major usability expansions, full Gemini branding across voice typing, custom clipboard retention timing, new Material 3 Expressive UI palettes, and high-refresh-rate display synchronization up to 144Hz.

---

## What is New in v1.0.2

### 1. Custom Clipboard History Retention
* Added a dedicated "Custom clipboard history retention" configuration directly under Settings > Clipboard.
* Supports both curated time presets and exact arbitrary hours input:
  * Presets: 1 Hour (default), 6 Hours, 12 Hours, 24 Hours (1 Day), 48 Hours (2 Days), 72 Hours (3 Days), 168 Hours (1 Week), 720 Hours (1 Month), and 8760 Hours (1 Year / Indefinite).
  * Custom numeric hours input with live days/hours computation and validation.
* Directly synchronizes with the internal clipboard retention worker engine.

### 2. Voice Typing Gemini Modernization
* Completely updated all internal and user-facing voice typing interfaces from legacy "Rambler" terminology to "Gemini".
* Updated settings titles, descriptions, assistant suggestions, and offline voice typing indicators across all supported application languages and locales.
* Preserved underlying offline and online voice recognition mechanics with zero functional regressions.

### 3. Gboard Pro Updates Renaming
* Renamed the root settings entry from "App Updates" to "Gboard Pro Updates" for improved brand consistency.
* Retains complete GitHub Releases integration, automatic update checks, in-app package downloading, and streamlined APK installer handoff.

### 4. Gboard UI Material 3 Expressive Palettes (Beta)
* Introduced the new "GBOARD UI (beta)" personalization suite within Settings > Appearance.
* Four distinct Material 3 Expressive color palettes:
  * Default Dynamic: Adaptive Material You dynamic tones matching your device wallpaper and Monet color extraction.
  * Expressive Rainbow: Mixed multi-color palette featuring vivid sunset coral, warm amber keycaps, and peach accents.
  * Fresh Botanical: Mixed multi-color palette featuring spring eucalyptus mint, soothing sage, and pastel clover accents.
  * Ocean Lavender: Mixed multi-color palette featuring coastal azure indigo, twilight lavender, and soft lilac accents.
* Live swatch previews and immediate real-time interface retheming.

### 5. High Refresh Rate Display Engine (144Hz / 120Hz / 90Hz)
* Resolved the display rendering lock where Gboard ran capped at 60Hz on high refresh rate displays.
* Integrated dynamic display mode querying and window attribute enforcement:
  * Enforces up to 144Hz on high-performance tablets (such as Xiaomi / Redmi Pad 7 Pro).
  * Enforces 120Hz on flagship and high-refresh smartphones.
  * Enforces 90Hz on supported mid-tier panels.
* Injected direct window attribute configuration (`preferredRefreshRate` and `preferredDisplayModeId`) into `onConfigureWindow` for the input method service.
* Added explicit high refresh rate metadata in `AndroidManifest.xml` for Xiaomi, MIUI, and AOSP window managers (`xiaomi.high_refresh_rate`, `miui.high_refresh_rate`, `android.view.DISPLAY_TOP_REFRESH_RATE`).

---

## Package Information
* Package Name: com.gboardpro.app
* Version: 1.0.2 (18.4.1.985164140-release-arm64-v8a)
* Architecture: arm64-v8a / Universal
* Minimum Android Version: Android 12 (API level 32)
* Target Android Version: Android 15 / Developer Preview (API level 37)
* Signature Scheme: v1, v2, and v3 signed with release keystore

## Release Assets
* `Gboard_Pro-1.0.2.apk`: Standard versioned package
* `Gboard_Pro-1.0.2-arm64-v8a.apk`: Optimized for 64-bit ARM architecture
* `Gboard_Pro-1.0.2-Universal.apk`: Universal architecture deployment package
* `Gboard_Pro.apk`: Canonical standalone release package
* `Gboard_Pro-1.0.2.mpp`: Morphe patch bundle for custom builder workflows
