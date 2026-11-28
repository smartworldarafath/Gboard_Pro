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

                            Text("Type").font(.system(size: 13.5)).foregroundColor(palette.textSecondary)
                            ForEach(Array(categories.enumerated()), id: \.offset) { idx, cat in
                                let sel = (selectedCategory == idx)
                                Button {
                                    selectedCategory = idx
                                } label: {
                                    HStack(spacing: 10) {
                                        Image(systemName: sel ? "largecircle.fill.circle" : "circle")
                                            .foregroundColor(sel ? palette.accent : palette.textSecondary)
                                        Text(cat)
                                            .font(.system(size: 15.5, weight: sel ? .bold : .medium))
                                            .foregroundColor(sel ? palette.accent : palette.textPrimary)
                                        Spacer()
                                    }
                                    .padding(12)
                                    .background(RoundedRectangle(cornerRadius: 12).fill(sel ? palette.accent.opacity(0.14) : palette.surfaceAlt))
                                    .overlay(RoundedRectangle(cornerRadius: 12).stroke(sel ? palette.accent : palette.surfaceStroke, lineWidth: 1))
                                }
                                .buttonStyle(.plain)
                            }
                        }
                        .padding(18)
                    }

                    SettingsCardGroup(palette: palette) {
                        VStack(alignment: .leading, spacing: 10) {
                            Text("MESSAGE")
                                .font(.system(size: 11.5, weight: .bold))
                                .tracking(0.8)
                                .foregroundColor(palette.accent)
                            TextField("Write message .....", text: $message, axis: .vertical)
                                .lineLimit(5...10)
                                .padding(12)
                                .background(RoundedRectangle(cornerRadius: 12).fill(palette.surfaceAlt))
                        }
