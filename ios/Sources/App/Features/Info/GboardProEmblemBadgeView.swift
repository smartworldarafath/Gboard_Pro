import SwiftUI

struct GboardProEmblemBadgeView: View {
    let size: CGFloat
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: size * 0.27, style: .continuous)
                .fill(LinearGradient(colors: [Color(hex: 0xF4F5FC), Color(hex: 0xE3E6F4)], startPoint: .topLeading, endPoint: .bottomTrailing))
                .overlay(RoundedRectangle(cornerRadius: size * 0.27).stroke(Color.white, lineWidth: 1.5))
                .shadow(color: Color.black.opacity(0.12), radius: 5, x: 0, y: 2)

            VStack(spacing: 4) {
                HStack(spacing: 3) {
                    ForEach(0..<4, id: \.self) { _ in
                        RoundedRectangle(cornerRadius: 2).fill(Color.white.opacity(0.85)).frame(width: size * 0.08, height: size * 0.08)
                    }
