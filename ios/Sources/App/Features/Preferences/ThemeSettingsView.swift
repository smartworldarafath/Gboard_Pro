import SwiftUI

struct ThemeSettingsView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette
    let openSandbox: () -> Void

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "UI STYLE & AMOLED") {
                        SettingsNavRow(
                            title: "Style",
                            summary: store.uiStyleMode.displayTitle,
                            icon: "drop",
                            palette: palette
                        ) {
                            store.uiStyleMode = (store.uiStyleMode == .classic) ? .liquidGlass : .classic
                        }
