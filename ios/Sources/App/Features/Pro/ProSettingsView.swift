import SwiftUI

struct ProSettingsView: View {
    let palette: GboardPalette
    let navigate: (SettingsRoute) -> Void

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "GBOARD PRO CUSTOMIZATIONS") {
                        SettingsNavRow(
                            title: "Amoled",
                            summary: "AMOLED Pure Black app & keyboard styling",
                            icon: "moon.stars",
                            palette: palette
                        ) { navigate(.amoled) }

