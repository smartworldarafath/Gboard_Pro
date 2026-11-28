import SwiftUI

struct AdditionalHubView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette
    let navigate: (SettingsRoute) -> Void

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "ADDITIONAL") {
                        SettingsNavRow(
                            title: "Choose UI",
                            summary: store.uiStyleMode == .liquidGlass
                                ? "3D Liquid Glass UI (Active)"
                                : "Classic Material UI (Default) (Active)",
                            icon: "paintpalette",
                            palette: palette
                        ) { navigate(.chooseUI) }

                        CardDivider(palette: palette)

                        SettingsNavRow(
                            title: "Notification",
                            summary: "App updates, beta release & beta feature alert settings",
                            icon: "bell",
                            palette: palette
                        ) { navigate(.notifications) }

