import SwiftUI

struct PreferencesSettingsView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "KEYS") {
                        SettingsToggleRow(
                            title: "Number row",
                            summary: "Show dedicated 1-0 number keys above letters",
                            icon: "number",
                            palette: palette,
                            isOn: $store.numberRowEnabled
                        )
                        CardDivider(palette: palette)
                        SettingsToggleRow(
                            title: "Pop-up on keypress",
                            summary: "Show floating magnification badge when tapping keys on iPhone",
                            icon: "arrow.up.square",
                            palette: palette,
                            isOn: $store.keyPressPopupEnabled
                        )
                        CardDivider(palette: palette)
                        SettingsToggleRow(
                            title: "One-handed mode",
                            summary: "Compact keyboard towards one thumb on large iPhones",
                            icon: "rectangle.lefthalf.inset.filled",
                            palette: palette,
                            isOn: $store.oneHandedMode
                        )
                    }
