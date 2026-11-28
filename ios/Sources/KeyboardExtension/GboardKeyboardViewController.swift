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

    override func textDidChange(_ textInput: UITextInput?) {
        super.textDidChange(textInput)
        syncAutoCapitalization()
        refreshSuggestions()
    }

    private func setupHierarchy() {
        rootStack.axis = .vertical
        rootStack.spacing = 6
        rootStack.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(rootStack)

        NSLayoutConstraint.activate([
            rootStack.topAnchor.constraint(equalTo: view.topAnchor, constant: 4),
            rootStack.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 3),
            rootStack.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -3),
            rootStack.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -4)
        ])

        topBarStack.axis = .horizontal
        topBarStack.alignment = .center
        topBarStack.spacing = 6
        topBarStack.heightAnchor.constraint(equalToConstant: 40).isActive = true

        keysContainer.axis = .vertical
        keysContainer.distribution = .fillEqually
        keysContainer.spacing = 6

        rootStack.addArrangedSubview(topBarStack)
        rootStack.addArrangedSubview(keysContainer)

        emojiCollectionContainer.isHidden = true
        clipboardContainer.isHidden = true
        rootStack.addArrangedSubview(emojiCollectionContainer)
        rootStack.addArrangedSubview(clipboardContainer)

        glideOverlay.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(glideOverlay)
        NSLayoutConstraint.activate([
            glideOverlay.topAnchor.constraint(equalTo: keysContainer.topAnchor),
            glideOverlay.leadingAnchor.constraint(equalTo: keysContainer.leadingAnchor),
            glideOverlay.trailingAnchor.constraint(equalTo: keysContainer.trailingAnchor),
            glideOverlay.bottomAnchor.constraint(equalTo: keysContainer.bottomAnchor)
        ])

        let pan = UIPanGestureRecognizer(target: self, action: #selector(handleGlidePan(_:)))
        pan.cancelsTouchesInView = false
        keysContainer.addGestureRecognizer(pan)

        popupBubble.isHidden = true
        popupBubble.textAlignment = .center
        popupBubble.font = .systemFont(ofSize: 26, weight: .medium)
        popupBubble.layer.cornerRadius = 10
        popupBubble.layer.masksToBounds = true
        view.addSubview(popupBubble)

        updateHeightConstraint()
    }

    private func updateHeightConstraint() {
        let isPad = traitCollection.userInterfaceIdiom == .pad
        let baseHeight: CGFloat = isPad ? 320 : 258
        let rowExtra: CGFloat = (store.numberRowEnabled && drawerMode == .letters) ? (isPad ? 48 : 38) : 0
        let barHeight: CGFloat = store.showSuggestionStrip ? 46 : 0
        let total = (baseHeight + rowExtra) * CGFloat(store.keyboardHeightScale) + barHeight

        if let existing = heightConstraint {
            existing.constant = total
        } else {
            let c = view.heightAnchor.constraint(equalToConstant: total)
            c.priority = UILayoutPriority(999)
            c.isActive = true
            heightConstraint = c
        }
    }

    private func rebuildAll() {
        let palette = currentPalette
        view.backgroundColor = UIColor(hex: palette.keyboardBackgroundHex)
        glideOverlay.trailColor = UIColor(palette.accent)
        buildTopBar(palette: palette)
        buildMainKeyboardGrid(palette: palette)
        buildEmojiDrawer(palette: palette)
        buildClipboardDrawer(palette: palette)
        applyDrawerVisibility()
        refreshSuggestions()
    }
