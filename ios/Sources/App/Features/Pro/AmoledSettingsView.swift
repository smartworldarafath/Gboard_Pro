import SwiftUI

struct AmoledSettingsView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "DISPLAY & THEMES") {
                        SettingsToggleRow(
                            title: "AMOLED Pure Black Mode",
                            summary: "True #000000 black background across the entire app UI and keyboard",
                            icon: "moon.fill",
                            palette: palette,
                            isOn: $store.amoledBlackEnabled
                        )
                    }
