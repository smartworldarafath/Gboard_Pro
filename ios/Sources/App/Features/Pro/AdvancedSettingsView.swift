import SwiftUI

struct AdvancedSettingsView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette
    @State private var smartWritingEnabled = true
    @State private var clipboardRetention = true

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "PRO MODULAR FEATURES") {
                        SettingsToggleRow(
                            title: "AI Writing Assistant UI",
                            summary: "Enable writing tools bar inside keyboard preview and toolbar",
                            icon: "wand.and.stars",
                            palette: palette,
                            isOn: $smartWritingEnabled
