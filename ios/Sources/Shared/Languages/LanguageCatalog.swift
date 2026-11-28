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
        KeyboardLanguageItem(
            code: "bn-BD",
            displayName: "বাংলা (বাংলাদেশ)",
            nativeName: "বাংলা (Avro / Phonetic)",
            layoutLabel: "বাংলা",
            isSuggested: true,
            rows: [
                ["া", "ি", "ু", "ে", "ো", "ক", "খ", "গ", "ঘ", "প"],
                ["অ", "আ", "ই", "উ", "এ", "চ", "জ", "ত", "দ"],
                ["ন", "ম", "ব", "য", "র", "ল", "স"]
            ]
        ),
        KeyboardLanguageItem(
            code: "ko-KR",
            displayName: "한국어",
            nativeName: "두벌식",
            layoutLabel: "한국어",
            isSuggested: true,
            rows: [
                ["ㅂ", "ㅈ", "ㄷ", "ㄱ", "ㅅ", "ㅛ", "ㅕ", "ㅑ", "ㅐ", "ㅔ"],
                ["ㅁ", "ㄴ", "ㅇ", "ㄹ", "ㅎ", "ㅗ", "ㅓ", "ㅏ", "ㅣ"],
                ["ㅋ", "ㅌ", "ㅊ", "ㅍ", "ㅠ", "ㅜ", "ㅡ"]
            ]
        ),
        KeyboardLanguageItem(
            code: "ar",
            displayName: "العربية",
