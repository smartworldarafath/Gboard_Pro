import SwiftUI

struct HelpFeedbackView: View {
    let palette: GboardPalette
    @State private var name = ""
    @State private var device = GboardSettingsStore.deviceModelSummary
    @State private var selectedCategory = 0
    @State private var message = ""
    @State private var showError = false

    private let categories = ["Feature request", "Bug report", "Feedback"]

    var body: some View {
        ZStack {
            palette.windowBackground.ignoresSafeArea()
            ScrollView {
                VStack(spacing: 14) {
                    SettingsCardGroup(palette: palette) {
                        VStack(alignment: .leading, spacing: 10) {
                            Text("DETAILS")
                                .font(.system(size: 11.5, weight: .bold))
                                .tracking(0.8)
                                .foregroundColor(palette.accent)
                            Text("Name").font(.system(size: 13.5)).foregroundColor(palette.textSecondary)
                            TextField("Enter your name", text: $name)
                                .padding(11)
                                .background(RoundedRectangle(cornerRadius: 12).fill(palette.surfaceAlt))

                            Text("Device").font(.system(size: 13.5)).foregroundColor(palette.textSecondary)
                            TextField("Device model", text: $device)
                                .padding(11)
                                .background(RoundedRectangle(cornerRadius: 12).fill(palette.surfaceAlt))

