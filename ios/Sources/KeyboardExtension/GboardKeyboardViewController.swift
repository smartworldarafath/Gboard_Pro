import UIKit
import SwiftUI

/// System-wide iOS & iPadOS Custom Keyboard Extension (UIInputViewController)
/// supporting Classic Material UI, 3D Liquid Glass UI, AMOLED Pure Black, Multi-Language Switching,
/// Adaptive iPhone + iPad Keyboards, Emoji/Flag Picker, Clipboard Snippets, Spacebar Cursor Glide, and Gesture Typing.
final class GboardKeyboardViewController: UIInputViewController {

    private enum ShiftState {
        case lowercase
        case shifted
        case capsLock
    }

    private enum DrawerMode {
        case letters
        case symbols
        case emoji
        case clipboard
    }

    private let store = GboardSettingsStore()
    private let rootStack = UIStackView()
    private let topBarStack = UIStackView()
    private let keysContainer = UIStackView()
    private let emojiCollectionContainer = UIView()
    private let clipboardContainer = UIView()
    private let glideOverlay = GlideTrailOverlayView()
    private let popupBubble = UILabel()

    private var suggestionButtons: [UIButton] = []
    private var letterButtons: [(button: LiquidGlassKeyButton, keyValue: String)] = []
    private var heightConstraint: NSLayoutConstraint?

    private var shiftState: ShiftState = .lowercase
    private var drawerMode: DrawerMode = .letters
    private var lastShiftTapTime: TimeInterval = 0
    private var lastSpaceTapTime: TimeInterval = 0
    private var backspaceTimer: Timer?
    private var hapticGenerator: UIImpactFeedbackGenerator?

    private var activeWordBuffer: String = ""
    private var glideSwipedKeys: [String] = []
    private var isGliding = false

    private var currentPalette: GboardPalette {
        let isDark = (traitCollection.userInterfaceStyle == .dark) || store.amoledBlackEnabled
        return GboardPalette.resolve(
            style: store.uiStyleMode,
            theme: store.selectedTheme,
            isDark: isDark,
            isAmoled: store.amoledBlackEnabled
        )
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        setupHierarchy()
        rebuildAll()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        store.reloadFromSharedDefaults()
        updateHeightConstraint()
        rebuildAll()
    }

    override func traitCollectionDidChange(_ previousTraitCollection: UITraitCollection?) {
        super.traitCollectionDidChange(previousTraitCollection)
        if previousTraitCollection?.userInterfaceStyle != traitCollection.userInterfaceStyle
            || previousTraitCollection?.horizontalSizeClass != traitCollection.horizontalSizeClass {
            updateHeightConstraint()
            rebuildAll()
        }
    }
