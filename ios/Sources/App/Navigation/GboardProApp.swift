import SwiftUI

@main
struct GboardProApp: App {
    @StateObject private var store = GboardSettingsStore.shared
    @Environment(\.colorScheme) private var systemColorScheme

    var body: some Scene {
        WindowGroup {
            RootSettingsView()
                .environmentObject(store)
                .preferredColorScheme(store.amoledBlackEnabled ? .dark : nil)
        }
    }
}

enum SettingsRoute: Hashable {
    case languages
    case preferences
    case theme
    case corrections
    case glideTyping
    case voiceTyping
    case emojiAndClipboard
    case pro
    case amoled
    case advanced
    case additional
    case chooseUI
    case notifications
    case permissions
    case reset
    case betaProgram
    case betaTesting
    case newBetaFeatures
    case updates
    case about
    case helpFeedback
    case liveSandbox
}

struct RootSettingsView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    @Environment(\.colorScheme) private var colorScheme
    @State private var path: [SettingsRoute] = []

    var palette: GboardPalette {
        GboardPalette.resolve(
            style: store.uiStyleMode,
            theme: store.selectedTheme,
            isDark: colorScheme == .dark || store.amoledBlackEnabled,
            isAmoled: store.amoledBlackEnabled
        )
    }

    var body: some View {
        NavigationStack(path: $path) {
            ZStack {
                palette.windowBackground.ignoresSafeArea()

                ScrollView {
                    VStack(alignment: .leading, spacing: 14) {
                        HStack {
                            Text("Gboard Pro Settings")
                                .font(.system(size: 30, weight: .bold))
                                .foregroundColor(palette.textPrimary)
                            Spacer()
                            Button {
                                path.append(.liveSandbox)
                            } label: {
                                HStack(spacing: 6) {
                                    Image(systemName: "keyboard")
                                    Text("Preview")
                                }
                                .font(.system(size: 13.5, weight: .semibold))
                                .foregroundColor(palette.accent)
                                .padding(.horizontal, 12)
                                .padding(.vertical, 8)
                                .background(Capsule().fill(palette.surfaceAlt))
                                .overlay(Capsule().stroke(palette.surfaceStroke, lineWidth: 1))
                            }
                            .accessibilityIdentifier("btn_preview_keyboard")
                        }
                        .padding(.horizontal, 20)
                        .padding(.top, 10)

                        SettingsCardGroup(palette: palette) {
                            SettingsNavRow(
                                title: "Languages",
                                summary: store.enabledLanguageCodes.map { LanguageCatalog.item(for: $0).displayName }.joined(separator: ", "),
                                icon: "globe",
                                palette: palette
                            ) { path.append(.languages) }

                            CardDivider(palette: palette)

                            SettingsNavRow(
                                title: "Preferences",
                                summary: "Number row, haptics, key popup & height",
                                icon: "slider.horizontal.3",
                                palette: palette
                            ) { path.append(.preferences) }

