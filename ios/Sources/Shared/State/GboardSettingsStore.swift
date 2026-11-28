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
