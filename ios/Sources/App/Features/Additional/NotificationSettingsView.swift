import SwiftUI
import UserNotifications

struct NotificationSettingsView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    let palette: GboardPalette

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 16) {
                    SettingsCardGroup(palette: palette, title: "NOTIFICATION PREFERENCES") {
                        SettingsToggleRow(
                            title: "App Update Notifications",
                            summary: store.notifyAppUpdatesAlert
                                ? "ON: Standard alert notification when a new app update releases"
                                : "OFF: Silent notification arrives quietly without sound or banner",
                            icon: "bell.badge",
                            palette: palette,
                            isOn: Binding(
                                get: { store.notifyAppUpdatesAlert },
                                set: { newValue in
                                    store.notifyAppUpdatesAlert = newValue
                                    if newValue { requestNotificationAuth() }
                                }
