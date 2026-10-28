## Overview

Gboard Pro v1.0.0 is the official initial release featuring an extensive Material 3 Expressive overhaul, custom launcher icon theming, native GitHub-integrated update mechanics, and comprehensive settings customization.

## Key Changes and Enhancements

### 1. Rebranding and Settings Interface
* Settings Header: Updated the main settings activity title to "Gboard Pro Settings".
* Advanced Settings Badge: Updated the version descriptor and pill indicator to "Gboard Pro 1.0.0".
* Spell Checker: Renamed all system spell-checker service labels and strings to "Gboard Pro Spell Checker".
* About Section: Redesigned the About activity to feature the full-resolution Gboard Pro icon, current build metadata (v1.0.0), and direct links to repository documentation, privacy policy, and licenses.
* Developer Integration: Integrated developer attribution for Arafath Rahman with dedicated navigation buttons to GitHub and Instagram.

### 2. Appearance and Launcher Icon Theming
* Added a dedicated "Appearance" section directly accessible from the root Settings menu.
* Implemented dynamic launcher icon switching supporting five distinct visual profiles:
  * Classic Gboard Pro (Default Material You dynamic theme)
  * Expressive Purple (High-contrast violet keycaps with vibrant glow)
  * Slate Charcoal (Minimalist neutral graphite finish)
  * Forest Emerald (Deep sage and emerald keycaps)
  * Midnight Navy (Cobalt blue and deep indigo typing accent)
* Integrated seamless runtime switching via Android activity aliases without requiring device restarts.

### 3. Vibration Strength Calibration
* Resolved the keypress vibration intensity defect where slider adjustments failed to modulate haptic motor voltage.
* Implemented dynamic amplitude scaling (1 to 255) and proportional duration mapping (5ms to 35ms), ensuring distinct sensory feedback across subtle, medium, and firm keystrokes.

### 4. Seamless Material 3 Window Transitions
* Implemented butter-smooth enter and exit transitions utilizing the FastOutSlowIn interpolator across all Settings and extension activities.
* Integrated system window animation styles to maintain visual fluidness throughout sub-screen navigation.

### 5. In-App Updates Engine
* Introduced the "App Updates" control center positioned directly above Advanced Settings.
* Automated release checking against the official GitHub Releases API.
* Direct in-app APK downloading with progress visualization.
* Automatic installation prompt upon package completion with a persistent installation fallback button.

### 6. Board UI Roadmap
* Introduced the "Board UI" preference section outlining upcoming visual personalization features currently in active development (keycap border tuning, custom font selection, and expressive key shapes).

### 7. Issue Reporting and Community Feedback
* Unified all "Help & feedback" preference touchpoints to navigate directly to the GitHub Issues portal for fast bug triage and feature requests.

## Package Information
* Package Name: com.gboardpro.app
* Target Version: 1.0.0 (18.4.1.985164140-release-arm64-v8a)
* Architecture: arm64-v8a
* Minimum Android Version: Android 12 (API level 32)
* Target Android Version: Android 15 / Developer Preview (API level 37)
* Signature Scheme: v3 signed with release keystore

## Assets
* Gboard_Pro-1.0.0.apk: Version-tagged release package
* Gboard_Pro.apk: Standalone release package
