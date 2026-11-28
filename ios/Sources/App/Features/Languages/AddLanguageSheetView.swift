import SwiftUI

struct AddLanguageSheetView: View {
    @EnvironmentObject private var store: GboardSettingsStore
    @Environment(\.dismiss) private var dismiss
    let palette: GboardPalette
    @State private var searchText = ""
    @State private var listAppeared = false

    private var filtered: [KeyboardLanguageItem] {
        let q = searchText.trimmingCharacters(in: .whitespacesAndNewlines).lowercased()
        if q.isEmpty { return LanguageCatalog.allLanguages }
        return LanguageCatalog.allLanguages.filter {
            $0.displayName.lowercased().contains(q) || $0.nativeName.lowercased().contains(q)
        }
    }

    var body: some View {
        NavigationStack {
            ZStack {
                palette.windowBackground.ignoresSafeArea()
                ScrollView {
                    VStack(spacing: 16) {
                        HStack(spacing: 10) {
                            Image(systemName: "magnifyingglass")
                                .foregroundColor(palette.textSecondary)
                            TextField("Search language", text: $searchText)
                                .foregroundColor(palette.textPrimary)
                        }
                        .padding(.horizontal, 14)
                        .padding(.vertical, 11)
                        .gboardCard(palette: palette, cornerRadius: 14)
                        .padding(.horizontal, 16)

                        let suggested = filtered.filter(\.isSuggested)
                        if !suggested.isEmpty {
                            SettingsCardGroup(palette: palette, title: "SUGGESTED LANGUAGES") {
                                ForEach(Array(suggested.enumerated()), id: \.element.id) { idx, lang in
                                    if idx > 0 { CardDivider(palette: palette) }
                                    languagePickRow(lang)
                                }
                            }
                        }

                        SettingsCardGroup(palette: palette, title: "ALL LANGUAGES") {
                            ForEach(Array(filtered.enumerated()), id: \.element.id) { idx, lang in
                                if idx > 0 { CardDivider(palette: palette) }
                                languagePickRow(lang)
                            }
                        }
                    }
                    .padding(.vertical, 12)
                    .offset(y: listAppeared ? 0 : 18)
                    .opacity(listAppeared ? 1 : 0)
                }
            }
            .navigationTitle("Add keyboard")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Done") { dismiss() }
                }
