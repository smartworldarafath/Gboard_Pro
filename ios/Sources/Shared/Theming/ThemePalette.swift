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

        let m3Stroke = isDark ? Color.white.opacity(0.12) : Color.black.opacity(0.08)

        switch theme {
        case .blushLilac:
            return GboardPalette(
                windowBackground: Color(hex: isDark ? 0x181122 : 0xF7EFFA),
                surface: Color(hex: isDark ? 0x241C2E : 0xFFFFFF),
                surfaceAlt: Color(hex: isDark ? 0x322840 : 0xEBD7FA),
                surfaceStroke: glass ? Color(hex: isDark ? 0xD0BCFF : 0x542983, alpha: isDark ? 0.28 : 0.20) : m3Stroke,
                textPrimary: Color(hex: isDark ? 0xF5EEFF : 0x24152F),
                textSecondary: Color(hex: isDark ? 0xCCC2DC : 0x5F4B6E),
                accent: Color(hex: isDark ? 0xD0BCFF : 0x542983),
                accentContainer: Color(hex: isDark ? 0x4F378B : 0xEBD7FA),
                onAccentContainer: Color(hex: isDark ? 0xEADDFF : 0x24152F),
                ctaForeground: glass ? Color(hex: isDark ? 0xF5F7FF : 0x1A2236) : Color(hex: isDark ? 0x24152F : 0xFFFFFF),
                isDark: isDark,
                isAmoled: false,
                isLiquidGlass: glass,
                orbBodyTopHex: isDark ? 0x3E3356 : 0xFFFFFF,
                orbBodyMidHex: isDark ? 0x191326 : 0xEFE5FA,
                orbBodyBotHex: isDark ? 0x31264A : 0xFAF6FE,
                keyBackgroundHex: isDark ? 0x2C233A : 0xFFFFFF,
                keyFunctionHex: isDark ? 0x3D2E54 : 0xE5D0F7,
                keyboardBackgroundHex: isDark ? 0x181122 : 0xF3E6FA
            )

        case .cottonCandy:
            return GboardPalette(
                windowBackground: Color(hex: isDark ? 0x1F1117 : 0xFDE7E9),
                surface: Color(hex: isDark ? 0x2E1922 : 0xFFFFFF),
                surfaceAlt: Color(hex: isDark ? 0x402330 : 0xF6B0CA),
                surfaceStroke: glass ? Color(hex: isDark ? 0xFF80AB : 0xB80058, alpha: isDark ? 0.28 : 0.20) : m3Stroke,
                textPrimary: Color(hex: isDark ? 0xFCE4EC : 0x2D121B),
                textSecondary: Color(hex: isDark ? 0xF8BBD0 : 0x6D4352),
                accent: Color(hex: isDark ? 0xFF80AB : 0xB80058),
                accentContainer: Color(hex: isDark ? 0x880E4F : 0xF6B0CA),
                onAccentContainer: Color(hex: isDark ? 0xFCE4EC : 0x2D121B),
                ctaForeground: glass ? Color(hex: isDark ? 0xF5F7FF : 0x1A2236) : Color(hex: isDark ? 0x2D121B : 0xFFFFFF),
                isDark: isDark,
                isAmoled: false,
                isLiquidGlass: glass,
                orbBodyTopHex: isDark ? 0x4E293A : 0xFFFFFF,
                orbBodyMidHex: isDark ? 0x201119 : 0xFCE0EA,
                orbBodyBotHex: isDark ? 0x3E202E : 0xFFF5F8,
                keyBackgroundHex: isDark ? 0x341D28 : 0xFFFFFF,
                keyFunctionHex: isDark ? 0x4E2638 : 0xF5C2D4,
                keyboardBackgroundHex: isDark ? 0x1F1117 : 0xFBE1E5
            )

        case .sunsetPeach:
            return GboardPalette(
                windowBackground: Color(hex: isDark ? 0x1C1310 : 0xFDE8E7),
                surface: Color(hex: isDark ? 0x281C17 : 0xFFFFFF),
                surfaceAlt: Color(hex: isDark ? 0x372720 : 0xFD987C),
                surfaceStroke: glass ? Color(hex: isDark ? 0xFF8A65 : 0xD35A2D, alpha: isDark ? 0.28 : 0.20) : m3Stroke,
                textPrimary: Color(hex: isDark ? 0xFBE9E7 : 0x2D1610),
                textSecondary: Color(hex: isDark ? 0xFFCCBC : 0x6E443A),
                accent: Color(hex: isDark ? 0xFF7043 : 0xD35A2D),
                accentContainer: Color(hex: isDark ? 0x5D2416 : 0xFFD9CC),
                onAccentContainer: Color(hex: isDark ? 0xFFDBCF : 0x2D1610),
                ctaForeground: glass ? Color(hex: isDark ? 0xF5F7FF : 0x1A2236) : Color(hex: isDark ? 0x2D1610 : 0xFFFFFF),
                isDark: isDark,
                isAmoled: false,
                isLiquidGlass: glass,
                orbBodyTopHex: isDark ? 0x483128 : 0xFFFFFF,
                orbBodyMidHex: isDark ? 0x1E1410 : 0xFDE4DC,
                orbBodyBotHex: isDark ? 0x3A2720 : 0xFFF7F4,
                keyBackgroundHex: isDark ? 0x2F211C : 0xFFFFFF,
                keyFunctionHex: isDark ? 0x462E24 : 0xFBD2C6,
                keyboardBackgroundHex: isDark ? 0x1C1310 : 0xFAE2DD
            )

