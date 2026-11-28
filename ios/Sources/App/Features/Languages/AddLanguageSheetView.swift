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
