import SwiftUI

struct EmojiAndClipboardSettingsView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette
    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "CLIPBOARD HISTORY") {
                        SettingsToggleRow(
                            title: "Clipboard history",
                            summary: "Store recent copied snippets locally inside shared App Group",
                            icon: "doc.on.clipboard",
                            palette: palette,
                            isOn: $store.clipboardHistoryEnabled
                        )
                    }
