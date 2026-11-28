import SwiftUI

/// Renders a 3D water-drop Liquid Glass orb matching Android's LiquidGlassOrbDrawable
/// (Light mode: frosted pearlescent droplet; Dark/AMOLED mode: deep smoked lens,
/// upper-left dome specular + angled wet glint, bottom-right caustic crescent, Fresnel rim).
public struct WaterDropOrbIcon: View {
    public let systemName: String
    public let palette: GboardPalette
    public var size: CGFloat = 44

    public init(systemName: String, palette: GboardPalette, size: CGFloat = 44) {
        self.systemName = systemName
        self.palette = palette
        self.size = size
    }

    public var body: some View {
        if palette.isLiquidGlass {
            ZStack {
                Circle()
                    .fill(
                        LinearGradient(
                            colors: [
                                Color(hex: palette.orbBodyTopHex),
                                Color(hex: palette.orbBodyMidHex),
                                Color(hex: palette.orbBodyBotHex)
                            ],
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                    )
                    .shadow(
                        color: palette.isAmoled ? .clear : Color.black.opacity(palette.isDark ? 0.34 : 0.08),
                        radius: palette.isDark ? 3.5 : 2.5,
                        x: 0,
                        y: 1.5
                    )

