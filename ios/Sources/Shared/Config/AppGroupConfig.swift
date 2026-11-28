import Foundation

public enum AppGroupConfig {
    public static let suiteName = "group.com.smartworldarafath.gboardpro"
    public static let bundleId = "com.smartworldarafath.gboardpro"
    public static let keyboardBundleId = "com.smartworldarafath.gboardpro.keyboard"
    public static let patchVersion = "2.2.0 (Beta 3)"
    public static let fullVersionName = "18.6.3-v2.2.0-beta3"
    public static let buildNumber = "176071777"
    public static let githubLatestReleaseAPI = "https://api.github.com/repos/smartworldarafath/Gboard_Pro/releases/latest"
    public static let githubReleasesListAPI = "https://api.github.com/repos/smartworldarafath/Gboard_Pro/releases?per_page=15"
    public static let githubIssuesNewURL = "https://github.com/smartworldarafath/Gboard_Pro/issues/new"
    public static let githubRepoWebURL = "https://github.com/smartworldarafath/Gboard_Pro"
    public static let telegramCommunityURL = "https://t.me/Gboard_Pro"

    public static var sharedDefaults: UserDefaults {
        UserDefaults(suiteName: suiteName) ?? .standard
    }
}

public enum PreferenceKeys {
    public static let uiStyleMode = "ui_style_mode_v220_b3" // "classic" | "liquid_glass"
    public static let selectedThemeKey = "selected_gboard_ui_theme"
    public static let amoledBlack = "amoled_black_enabled"
    public static let enabledLanguages = "enabled_keyboard_languages_v1"
    public static let activeLanguageCode = "active_keyboard_language_code_v1"

    // Keyboard Preferences
    public static let numberRow = "pref_number_row"
    public static let keyPressPopup = "pref_keypress_popup"
    public static let hapticFeedback = "pref_haptic_feedback"
    public static let hapticStrength = "pref_haptic_strength"
    public static let keySound = "pref_key_sound"
    public static let oneHandedMode = "pref_one_handed_mode"
    public static let keyboardHeightScale = "pref_keyboard_height_scale"

