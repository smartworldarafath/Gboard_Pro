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
