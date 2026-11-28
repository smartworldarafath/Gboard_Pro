import Foundation

public struct KeyboardLanguageItem: Identifiable, Hashable, Codable {
    public let code: String
    public let displayName: String
    public let nativeName: String
    public let layoutLabel: String
    public let isSuggested: Bool
    public let rows: [[String]]

    public var id: String { code }
}

public enum LanguageCatalog {
    public static let allLanguages: [KeyboardLanguageItem] = [
        KeyboardLanguageItem(
            code: "en-US",
            displayName: "English (US)",
            nativeName: "QWERTY",
            layoutLabel: "English",
            isSuggested: true,
            rows: [
                ["q", "w", "e", "r", "t", "y", "u", "i", "o", "p"],
                ["a", "s", "d", "f", "g", "h", "j", "k", "l"],
                ["z", "x", "c", "v", "b", "n", "m"]
            ]
        ),
