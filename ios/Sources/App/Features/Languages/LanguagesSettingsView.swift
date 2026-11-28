import SwiftUI

struct LanguagesSettingsView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette
    @State private var showAddLanguageSheet = false

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "ENABLED KEYBOARDS") {
                        ForEach(Array(store.enabledLanguageCodes.enumerated()), id: \.element) { idx, code in
                            if idx > 0 { CardDivider(palette: palette) }
                            let item = LanguageCatalog.item(for: code)
                            HStack(spacing: 12) {
                                WaterDropOrbIcon(systemName: "character.bubble", palette: palette, size: 40)
                                VStack(alignment: .leading, spacing: 2) {
                                    Text(item.displayName)
                                        .font(.system(size: 16, weight: .medium))
                                        .foregroundColor(palette.textPrimary)
                                    Text(item.nativeName)
                                        .font(.system(size: 13))
                                        .foregroundColor(palette.textSecondary)
                                }
                                Spacer()
                                if store.activeLanguageCode == code {
                                    Text("Active")
                                        .font(.system(size: 11.5, weight: .bold))
                                        .foregroundColor(palette.accent)
                                        .padding(.horizontal, 9)
                                        .padding(.vertical, 4)
                                        .background(Capsule().fill(palette.accent.opacity(0.14)))
                                }
                                if store.enabledLanguageCodes.count > 1 {
                                    Button(role: .destructive) {
                                        withAnimation(.spring(response: 0.3, dampingFraction: 0.85)) {
                                            store.removeLanguage(code)
                                        }
                                    } label: {
                                        Image(systemName: "trash")
                                            .font(.system(size: 14))
                                            .foregroundColor(palette.textSecondary)
                                            .padding(8)
                                    }
                                    .buttonStyle(.plain)
                                }
                            }
