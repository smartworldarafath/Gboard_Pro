import SwiftUI

struct BetaProgramHubView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette
    let navigate: (SettingsRoute) -> Void

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "BETA PROGRAM") {
                        SettingsNavRow(
                            title: "Beta version testing",
                            summary: "Pre-release beta builds, testing features & beta bug reports",
                            icon: "flask",
                            palette: palette
                        ) { navigate(.betaTesting) }

