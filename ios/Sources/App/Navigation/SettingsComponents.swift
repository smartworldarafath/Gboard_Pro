import SwiftUI

struct SettingsCardGroup<Content: View>: View {
    let palette: GboardPalette
    let title: String?
    @ViewBuilder let content: () -> Content

    init(palette: GboardPalette, title: String? = nil, @ViewBuilder content: @escaping () -> Content) {
        self.palette = palette
        self.title = title
        self.content = content
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            if let title {
                Text(title)
                    .font(.system(size: 12, weight: .bold))
                    .tracking(0.8)
                    .foregroundColor(palette.accent)
                    .padding(.horizontal, 22)
            }
            VStack(spacing: 0) {
                content()
            }
            .gboardCard(palette: palette)
            .padding(.horizontal, 16)
        }
    }
}

struct SettingsNavRow: View {
    let title: String
    let summary: String
    let icon: String
    let palette: GboardPalette
    let action: () -> Void

