import SwiftUI

/// Interactive SwiftUI keyboard preview embedded inside host app so testing works immediately without switching system keyboards.
struct InAppKeyboardPreviewContainer: View {
    let palette: GboardPalette
    @ObservedObject var store: GboardSettingsStore
    let onInsertText: (String) -> Void
    let onBackspace: () -> Void

    @State private var shifted = false
    @State private var symbolsMode = false
    @State private var emojiMode = false

    private var activeLang: KeyboardLanguageItem {
        LanguageCatalog.item(for: store.activeLanguageCode)
    }

    var body: some View {
        VStack(spacing: 6) {
            if store.showSuggestionStrip {
                HStack(spacing: 8) {
                    WaterDropOrbIcon(systemName: "g.circle.fill", palette: palette, size: 32)
                    Spacer()
                    ForEach(GboardSuggestionEngine.suggestions(for: ""), id: \.self) { word in
                        Button {
                            onInsertText(word + " ")
                        } label: {
                            Text(word)
                                .font(.system(size: 14, weight: .medium))
                                .foregroundColor(palette.textPrimary)
                                .padding(.horizontal, 12)
                                .padding(.vertical, 6)
                        }
