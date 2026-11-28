import SwiftUI
import PhotosUI

struct BetaVersionTestingView: View {
    let palette: GboardPalette
    @StateObject private var service = ReleaseAndBetaService.shared
    @State private var issueTitle = ""
    @State private var issueDescription = ""
    @State private var deviceModel = GboardSettingsStore.deviceModelSummary
    @State private var pickerItems: [PhotosPickerItem] = []
    @State private var selectedImages: [(name: String, image: UIImage)] = []
    @State private var alertMessage: String?

    private var betaInstalled: Bool {
        GboardSettingsStore.isBetaVersionInstalled
    }

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 14) {
                    // 1. Pre-release English Testing Notice
                    SettingsCardGroup(palette: palette) {
                        VStack(alignment: .leading, spacing: 8) {
                            cardLabel("BETA TESTING NOTICE")
                            Text("Beta versions are released here for testing before an upcoming stable version launches. Because these builds are pre-release test versions, app crashes, unexpected glitches, or features not working properly may happen and bugs can be present. If you would like to test new upcoming versions prior to the next stable release, you can download and use them from here.")
                                .font(.system(size: 13.8))
                                .foregroundColor(palette.textSecondary)
                                .lineSpacing(3)
                        }
                        .padding(18)
                    }

                    // 2. Beta Build Check / Download Card
                    SettingsCardGroup(palette: palette) {
                        VStack(alignment: .leading, spacing: 10) {
                            cardLabel("BETA BUILD RELEASE")
                            betaReleaseContent()
                        }
