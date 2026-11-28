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

