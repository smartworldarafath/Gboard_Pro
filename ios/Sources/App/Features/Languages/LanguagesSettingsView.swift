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
