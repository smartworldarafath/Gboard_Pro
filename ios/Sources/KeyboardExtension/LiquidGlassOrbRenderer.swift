import UIKit

enum KeyboardOrbRole {
    case normal
    case functionKey
    case accentAction
    case orbButton
}

/// Memory-efficient CoreGraphics keycap / water-drop orb view tailored for iOS Keyboard Extension (~50 MB watchdog safe).
final class LiquidGlassKeyButton: UIButton {
    var palette: GboardPalette? {
        didSet { setNeedsDisplay() }
    }
    var orbRole: KeyboardOrbRole = .normal {
        didSet { setNeedsDisplay() }
    }
    var isCircularOrb: Bool = false {
        didSet { setNeedsDisplay() }
    }

    override var isHighlighted: Bool {
        didSet {
            if oldValue != isHighlighted {
                setNeedsDisplay()
            }
        }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        contentMode = .redraw
        isOpaque = false
        backgroundColor = .clear
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func draw(_ rect: CGRect) {
        guard let ctx = UIGraphicsGetCurrentContext(), let palette else { return }
        let insetRect = rect.insetBy(dx: 0.5, dy: 0.5)
        let radius = isCircularOrb
            ? min(insetRect.width, insetRect.height) / 2.0
            : (palette.isLiquidGlass ? 11.0 : 6.0)

        let path = UIBezierPath(roundedRect: insetRect, cornerRadius: radius)

        if !palette.isLiquidGlass {
            let fillColor: UIColor
            switch orbRole {
            case .accentAction:
                fillColor = UIColor(palette.accent)
            case .functionKey, .orbButton:
                fillColor = UIColor(hex: palette.keyFunctionHex)
            case .normal:
                fillColor = UIColor(hex: palette.keyBackgroundHex)
            }
            let effective = isHighlighted ? fillColor.withAlphaComponent(0.76) : fillColor
            ctx.saveGState()
            if !palette.isAmoled {
                ctx.setShadow(
                    offset: CGSize(width: 0, height: 1),
                    blur: 0.5,
                    color: UIColor.black.withAlphaComponent(palette.isDark ? 0.30 : 0.18).cgColor
                )
            }
