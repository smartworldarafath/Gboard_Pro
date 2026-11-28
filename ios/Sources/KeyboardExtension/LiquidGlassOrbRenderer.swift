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
