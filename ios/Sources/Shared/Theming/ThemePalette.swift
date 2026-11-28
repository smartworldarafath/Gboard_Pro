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

        if isAmoled {
            let accentColor: Color
            let accentContainerColor: Color
            switch theme {
            case .blushLilac:
                accentColor = Color(hex: 0xD0BCFF)
                accentContainerColor = Color(hex: 0x2B1E4A)
            case .cottonCandy:
                accentColor = Color(hex: 0xFF80AB)
                accentContainerColor = Color(hex: 0x4A1228)
            case .sunsetPeach:
                accentColor = Color(hex: 0xFF8A65)
                accentContainerColor = Color(hex: 0x461E14)
            case .freshMint:
                accentColor = Color(hex: 0x80CBC4)
                accentContainerColor = Color(hex: 0x0E352F)
            case .ocean, .systemDefault:
                accentColor = Color(hex: 0xA8C7FA)
                accentContainerColor = Color(hex: 0x142238)
            }

            if !glass {
                return GboardPalette(
                    windowBackground: Color(hex: 0x000000),
                    surface: Color(hex: 0x141518),
                    surfaceAlt: Color(hex: 0x1F2126),
                    surfaceStroke: Color.white.opacity(0.14),
                    textPrimary: Color(hex: 0xE6E1E5),
                    textSecondary: Color(hex: 0xCAC4D0),
                    accent: accentColor,
                    accentContainer: accentContainerColor,
                    onAccentContainer: Color(hex: 0xD3E3FD),
                    ctaForeground: Color(hex: 0x09162B),
                    isDark: true,
                    isAmoled: true,
                    isLiquidGlass: false,
                    orbBodyTopHex: 0x273046,
                    orbBodyMidHex: 0x06080E,
                    orbBodyBotHex: 0x1B2336,
                    keyBackgroundHex: 0x1C1E24,
                    keyFunctionHex: 0x121418,
                    keyboardBackgroundHex: 0x000000
                )
            }
            return GboardPalette(
                windowBackground: Color(hex: 0x000000),
                surface: Color(hex: 0x0A0C12),
                surfaceAlt: Color(hex: 0x10131C),
                surfaceStroke: Color(hex: 0xA8C7FA, alpha: 0.25),
                textPrimary: Color(hex: 0xF5F7FF),
                textSecondary: Color(hex: 0xB0B8D0),
                accent: accentColor,
                accentContainer: accentContainerColor,
                onAccentContainer: Color(hex: 0xD8E8FF),
                ctaForeground: Color(hex: 0xF5F7FF),
                isDark: true,
                isAmoled: true,
                isLiquidGlass: true,
                orbBodyTopHex: 0x273046,
                orbBodyMidHex: 0x06080E,
                orbBodyBotHex: 0x1B2336,
                keyBackgroundHex: 0x131722,
                keyFunctionHex: 0x0C0F18,
                keyboardBackgroundHex: 0x000000
            )
        }
