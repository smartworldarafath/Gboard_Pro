import SwiftUI

struct ResetSettingsView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette
    @State private var showResetDone = false

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "RESET SETTINGS") {
                        VStack(alignment: .leading, spacing: 12) {
                            Text("Restore Gboard Pro Defaults")
                                .font(.system(size: 16, weight: .bold))
                                .foregroundColor(palette.textPrimary)
                            Text("Resets UI style back to Classic Material UI, clears custom theme overrides, and restores default keyboard & notification settings.")
                                .font(.system(size: 13.5))
                                .foregroundColor(palette.textSecondary)
                            GboardPillButton(title: "Reset All Preferences", systemImage: "arrow.counterclockwise", palette: palette) {
                                store.resetAllPreferences()
                                showResetDone = true
                            }
