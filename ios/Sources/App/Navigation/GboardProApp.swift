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

