import Foundation
#if canImport(Combine)
import Combine
#endif
#if canImport(UIKit)
import UIKit
#endif

public final class GboardSettingsStore: ObservableObject {
    public static let shared = GboardSettingsStore()

    private let defaults: UserDefaults

    @Published public var uiStyleMode: UIStyleMode {
        didSet { defaults.set(uiStyleMode.rawValue, forKey: PreferenceKeys.uiStyleMode) }
    }
    @Published public var selectedTheme: KeyboardThemeOption {
        didSet { defaults.set(selectedTheme.rawValue, forKey: PreferenceKeys.selectedThemeKey) }
    }
    @Published public var amoledBlackEnabled: Bool {
        didSet { defaults.set(amoledBlackEnabled, forKey: PreferenceKeys.amoledBlack) }
    }
    @Published public var enabledLanguageCodes: [String] {
        didSet { defaults.set(enabledLanguageCodes, forKey: PreferenceKeys.enabledLanguages) }
    }
    @Published public var activeLanguageCode: String {
        didSet { defaults.set(activeLanguageCode, forKey: PreferenceKeys.activeLanguageCode) }
    }

    // Keyboard Preferences
    @Published public var numberRowEnabled: Bool {
        didSet { defaults.set(numberRowEnabled, forKey: PreferenceKeys.numberRow) }
    }
    @Published public var keyPressPopupEnabled: Bool {
        didSet { defaults.set(keyPressPopupEnabled, forKey: PreferenceKeys.keyPressPopup) }
    }
    @Published public var hapticFeedbackEnabled: Bool {
        didSet { defaults.set(hapticFeedbackEnabled, forKey: PreferenceKeys.hapticFeedback) }
    }
    @Published public var hapticStrength: Double {
        didSet { defaults.set(hapticStrength, forKey: PreferenceKeys.hapticStrength) }
    }
    @Published public var keySoundEnabled: Bool {
        didSet { defaults.set(keySoundEnabled, forKey: PreferenceKeys.keySound) }
    }
    @Published public var oneHandedMode: Bool {
        didSet { defaults.set(oneHandedMode, forKey: PreferenceKeys.oneHandedMode) }
    }
    @Published public var keyboardHeightScale: Double {
        didSet { defaults.set(keyboardHeightScale, forKey: PreferenceKeys.keyboardHeightScale) }
    }

    // Corrections & Suggestions
    @Published public var showSuggestionStrip: Bool {
        didSet { defaults.set(showSuggestionStrip, forKey: PreferenceKeys.showSuggestionStrip) }
    }
    @Published public var autoCorrectionEnabled: Bool {
        didSet { defaults.set(autoCorrectionEnabled, forKey: PreferenceKeys.autoCorrection) }
    }
    @Published public var autoCapitalizationEnabled: Bool {
        didSet { defaults.set(autoCapitalizationEnabled, forKey: PreferenceKeys.autoCapitalization) }
    }
    @Published public var doubleSpacePeriodEnabled: Bool {
        didSet { defaults.set(doubleSpacePeriodEnabled, forKey: PreferenceKeys.doubleSpacePeriod) }
    }

    // Glide Typing
    @Published public var glideTypingEnabled: Bool {
        didSet { defaults.set(glideTypingEnabled, forKey: PreferenceKeys.glideTyping) }
    }
    @Published public var showGestureTrail: Bool {
        didSet { defaults.set(showGestureTrail, forKey: PreferenceKeys.showGestureTrail) }
    }

    // Clipboard
    @Published public var clipboardHistoryEnabled: Bool {
        didSet { defaults.set(clipboardHistoryEnabled, forKey: PreferenceKeys.clipboardHistoryEnabled) }
    }
    @Published public var clipboardItems: [String] {
        didSet { defaults.set(clipboardItems, forKey: PreferenceKeys.clipboardItems) }
    }

    // Notifications (Android Beta 3 parity)
    @Published public var notifyAppUpdatesAlert: Bool {
        didSet { defaults.set(notifyAppUpdatesAlert, forKey: PreferenceKeys.notifyAppUpdates) }
    }
    @Published public var notifyBetaApkAlert: Bool {
        didSet { defaults.set(notifyBetaApkAlert, forKey: PreferenceKeys.notifyBetaBuilds) }
    }
    @Published public var notifyBetaFeatures: Bool {
        didSet { defaults.set(notifyBetaFeatures, forKey: PreferenceKeys.notifyBetaFeatures) }
    }

    // Beta Program
    @Published public var betaNewFeaturesSliderEnabled: Bool {
        didSet { defaults.set(betaNewFeaturesSliderEnabled, forKey: PreferenceKeys.betaNewFeaturesSlider) }
    }

    public init(defaults: UserDefaults = AppGroupConfig.sharedDefaults) {
        self.defaults = defaults

        let styleRaw = defaults.string(forKey: PreferenceKeys.uiStyleMode) ?? UIStyleMode.classic.rawValue
        self.uiStyleMode = UIStyleMode(rawValue: styleRaw) ?? .classic

        let themeRaw = defaults.string(forKey: PreferenceKeys.selectedThemeKey) ?? KeyboardThemeOption.systemDefault.rawValue
        self.selectedTheme = KeyboardThemeOption(rawValue: themeRaw) ?? .systemDefault

        self.amoledBlackEnabled = defaults.object(forKey: PreferenceKeys.amoledBlack) as? Bool ?? false

        let savedLangs = defaults.stringArray(forKey: PreferenceKeys.enabledLanguages) ?? ["en-US", "bn-BD", "ko-KR"]
        self.enabledLanguageCodes = savedLangs.isEmpty ? ["en-US"] : savedLangs

        let savedActive = defaults.string(forKey: PreferenceKeys.activeLanguageCode) ?? savedLangs.first ?? "en-US"
        self.activeLanguageCode = savedLangs.contains(savedActive) ? savedActive : (savedLangs.first ?? "en-US")

        self.numberRowEnabled = defaults.object(forKey: PreferenceKeys.numberRow) as? Bool ?? true
        self.keyPressPopupEnabled = defaults.object(forKey: PreferenceKeys.keyPressPopup) as? Bool ?? true
        self.hapticFeedbackEnabled = defaults.object(forKey: PreferenceKeys.hapticFeedback) as? Bool ?? true
        self.hapticStrength = defaults.object(forKey: PreferenceKeys.hapticStrength) as? Double ?? 0.65
        self.keySoundEnabled = defaults.object(forKey: PreferenceKeys.keySound) as? Bool ?? false
        self.oneHandedMode = defaults.object(forKey: PreferenceKeys.oneHandedMode) as? Bool ?? false
        self.keyboardHeightScale = defaults.object(forKey: PreferenceKeys.keyboardHeightScale) as? Double ?? 1.0

        self.showSuggestionStrip = defaults.object(forKey: PreferenceKeys.showSuggestionStrip) as? Bool ?? true
        self.autoCorrectionEnabled = defaults.object(forKey: PreferenceKeys.autoCorrection) as? Bool ?? true
        self.autoCapitalizationEnabled = defaults.object(forKey: PreferenceKeys.autoCapitalization) as? Bool ?? true
        self.doubleSpacePeriodEnabled = defaults.object(forKey: PreferenceKeys.doubleSpacePeriod) as? Bool ?? true

        self.glideTypingEnabled = defaults.object(forKey: PreferenceKeys.glideTyping) as? Bool ?? true
        self.showGestureTrail = defaults.object(forKey: PreferenceKeys.showGestureTrail) as? Bool ?? true

        self.clipboardHistoryEnabled = defaults.object(forKey: PreferenceKeys.clipboardHistoryEnabled) as? Bool ?? true
        self.clipboardItems = defaults.stringArray(forKey: PreferenceKeys.clipboardItems) ?? [
            "Gboard Pro iOS v2.2.0 (Beta 3)",
            "Hello from Gboard Pro!"
        ]

