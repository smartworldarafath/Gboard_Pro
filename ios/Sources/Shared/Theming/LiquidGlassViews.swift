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

                // Clipped water-droplet internal optics
                ZStack {
                    RadialGradient(
                        colors: [
                            Color.white.opacity(palette.isDark ? (palette.isAmoled ? 0.42 : 0.48) : 0.85),
                            Color.white.opacity(palette.isDark ? 0.14 : 0.0),
                            Color.clear
                        ],
                        center: UnitPoint(x: 0.32, y: 0.31),
                        startRadius: 0,
                        endRadius: size * 0.44
                    )

                    if palette.isDark || palette.isAmoled {
                        // Crisp angled wet pinpoint glint for dark water droplet realism
                        Ellipse()
                            .fill(
                                RadialGradient(
                                    colors: [Color.white.opacity(0.72), Color.white.opacity(0.22), Color.clear],
                                    center: .center,
                                    startRadius: 0,
                                    endRadius: size * 0.16
                                )
                            )
                            .frame(width: size * 0.30, height: size * 0.15)
                            .rotationEffect(.degrees(-36))
                            .offset(x: -size * 0.17, y: -size * 0.18)

                        // Bottom-right internal caustic bounce
                        RadialGradient(
                            colors: [
                                palette.accent.opacity(palette.isAmoled ? 0.36 : 0.42),
                                Color.white.opacity(0.12),
                                Color.clear
                            ],
                            center: UnitPoint(x: 0.62, y: 0.77),
                            startRadius: 0,
                            endRadius: size * 0.34
                        )
