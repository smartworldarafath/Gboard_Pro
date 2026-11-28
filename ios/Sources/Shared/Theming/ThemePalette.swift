import SwiftUI

public enum KeyboardThemeOption: String, CaseIterable, Identifiable {
    case systemDefault = "default"
    case blushLilac = "blush_lilac"
    case cottonCandy = "cotton_candy"
    case sunsetPeach = "sunset_peach"
    case freshMint = "fresh_mint"
    case ocean = "ocean"

    public var id: String { rawValue }

    public var title: String {
        switch self {
        case .systemDefault: return "Dynamic System"
        case .blushLilac: return "Blush Lilac"
        case .cottonCandy: return "Cotton Candy"
        case .sunsetPeach: return "Sunset Peach"
        case .freshMint: return "Fresh Mint"
        case .ocean: return "Ocean Sapphire"
        }
    }

    public var swatchColor: Color {
        switch self {
        case .systemDefault: return Color(hex: 0x283CF5)
        case .blushLilac: return Color(hex: 0x7C4DFF)
        case .cottonCandy: return Color(hex: 0xD81B60)
        case .sunsetPeach: return Color(hex: 0xFF7043)
        case .freshMint: return Color(hex: 0x00897B)
        case .ocean: return Color(hex: 0x3949AB)
        }
    }
}

public struct GboardPalette: Equatable {
    public let windowBackground: Color
    public let surface: Color
    public let surfaceAlt: Color
    public let surfaceStroke: Color
    public let textPrimary: Color
    public let textSecondary: Color
    public let accent: Color
    public let accentContainer: Color
    public let onAccentContainer: Color
    public let ctaForeground: Color
    public let isDark: Bool
    public let isAmoled: Bool
    public let isLiquidGlass: Bool

    public let orbBodyTopHex: UInt32
    public let orbBodyMidHex: UInt32
    public let orbBodyBotHex: UInt32
    public let keyBackgroundHex: UInt32
    public let keyFunctionHex: UInt32
    public let keyboardBackgroundHex: UInt32

    public static func resolve(
        style: UIStyleMode,
        theme: KeyboardThemeOption,
        isDark: Bool,
        isAmoled: Bool
    ) -> GboardPalette {
        let glass = (style == .liquidGlass)

