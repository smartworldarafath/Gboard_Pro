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

    private func buildTopBar(palette: GboardPalette) {
        topBarStack.isHidden = !store.showSuggestionStrip
        topBarStack.arrangedSubviews.forEach { $0.removeFromSuperview() }
        suggestionButtons.removeAll()

        let gBtn = makeToolbarOrbButton(systemName: "g.circle.fill", palette: palette) { [weak self] in
            self?.openHostSettings()
        }
        topBarStack.addArrangedSubview(gBtn)

        let suggestionsHolder = UIStackView()
        suggestionsHolder.axis = .horizontal
        suggestionsHolder.distribution = .fillEqually
        suggestionsHolder.spacing = 4
        for idx in 0..<3 {
            let b = UIButton(type: .system)
            b.titleLabel?.font = .systemFont(ofSize: 15, weight: .medium)
            b.setTitleColor(UIColor(palette.textPrimary), for: .normal)
            b.tag = idx
            b.addTarget(self, action: #selector(handleSuggestionTap(_:)), for: .touchUpInside)
            suggestionButtons.append(b)
            suggestionsHolder.addArrangedSubview(b)
        }
        topBarStack.addArrangedSubview(suggestionsHolder)

        let clipBtn = makeToolbarOrbButton(systemName: "doc.on.clipboard", palette: palette) { [weak self] in
            guard let self else { return }
            self.drawerMode = (self.drawerMode == .clipboard) ? .letters : .clipboard
            self.applyDrawerVisibility()
        }
        let emojiBtn = makeToolbarOrbButton(
            systemName: drawerMode == .emoji ? "keyboard" : "face.smiling",
            palette: palette
        ) { [weak self] in
            guard let self else { return }
            self.drawerMode = (self.drawerMode == .emoji) ? .letters : .emoji
            self.applyDrawerVisibility()
        }
        topBarStack.addArrangedSubview(clipBtn)
        topBarStack.addArrangedSubview(emojiBtn)
    }

    private func makeToolbarOrbButton(systemName: String, palette: GboardPalette, action: @escaping () -> Void) -> LiquidGlassKeyButton {
        let btn = LiquidGlassKeyButton(type: .system)
        btn.palette = palette
        btn.orbRole = .orbButton
        btn.isCircularOrb = palette.isLiquidGlass
        btn.tintColor = UIColor(palette.textPrimary)
        btn.setImage(UIImage(systemName: systemName), for: .normal)
        btn.widthAnchor.constraint(equalToConstant: 36).isActive = true
        btn.heightAnchor.constraint(equalToConstant: 36).isActive = true
        btn.addAction(UIAction { _ in action() }, for: .touchUpInside)
        return btn
    }

    private func buildMainKeyboardGrid(palette: GboardPalette) {
        keysContainer.arrangedSubviews.forEach { $0.removeFromSuperview() }
        letterButtons.removeAll()

        let isPad = traitCollection.userInterfaceIdiom == .pad
        let lang = LanguageCatalog.item(for: store.activeLanguageCode)
        let showNumbers = store.numberRowEnabled && drawerMode == .letters

        if showNumbers {
            keysContainer.addArrangedSubview(makeCharacterRow(LanguageCatalog.numberRow, palette: palette))
        }

        let rows = (drawerMode == .symbols) ? LanguageCatalog.symbolRows : lang.rows
        for (idx, rowKeys) in rows.enumerated() {
            let rowStack = UIStackView()
            rowStack.axis = .horizontal
            rowStack.spacing = isPad ? 7 : 5
            rowStack.distribution = .fill

            if idx == rows.count - 1 {
                let leftShift = makeSpecialButton(
                    title: drawerMode == .symbols ? "ABC" : nil,
                    systemName: drawerMode == .symbols ? nil : shiftIconName(),
                    palette: palette,
                    role: .functionKey,
                    widthMultiplier: isPad ? 1.5 : 1.35
                ) { [weak self] in
                    self?.handleShiftOrSymbolToggle()
                }
                rowStack.addArrangedSubview(leftShift)
            }

            let innerStack = makeCharacterRow(rowKeys, palette: palette)
            rowStack.addArrangedSubview(innerStack)

            if idx == rows.count - 1 {
                let deleteBtn = makeSpecialButton(
                    title: nil,
                    systemName: "delete.left",
                    palette: palette,
                    role: .functionKey,
                    widthMultiplier: isPad ? 1.5 : 1.35
                ) { [weak self] in
                    self?.performBackspace()
                }
                let longPress = UILongPressGestureRecognizer(target: self, action: #selector(handleBackspaceLongPress(_:)))
                longPress.minimumPressDuration = 0.25
                deleteBtn.addGestureRecognizer(longPress)
                rowStack.addArrangedSubview(deleteBtn)
            }

            keysContainer.addArrangedSubview(rowStack)
        }

        keysContainer.addArrangedSubview(makeBottomControlRow(lang: lang, palette: palette, isPad: isPad))
    }

    private func makeCharacterRow(_ chars: [String], palette: GboardPalette) -> UIStackView {
        let isPad = traitCollection.userInterfaceIdiom == .pad
        let stack = UIStackView()
        stack.axis = .horizontal
        stack.distribution = .fillEqually
        stack.spacing = isPad ? 7 : 5

        for ch in chars {
            let btn = LiquidGlassKeyButton(type: .system)
            btn.palette = palette
            btn.orbRole = .normal
            let displayed = (drawerMode == .letters && shiftState != .lowercase) ? ch.uppercased() : ch
            btn.setTitle(displayed, for: .normal)
            btn.setTitleColor(UIColor(palette.textPrimary), for: .normal)
            btn.titleLabel?.font = .systemFont(ofSize: isPad ? 22 : 20, weight: .regular)
            btn.addTarget(self, action: #selector(handleKeyTouchDown(_:)), for: .touchDown)
            btn.addTarget(self, action: #selector(handleCharacterKeyTap(_:)), for: .touchUpInside)
            btn.addTarget(self, action: #selector(handleKeyTouchCancel(_:)), for: [.touchUpOutside, .touchCancel])
            letterButtons.append((btn, ch))
            stack.addArrangedSubview(btn)
        }
        return stack
    }

    private func makeBottomControlRow(lang: KeyboardLanguageItem, palette: GboardPalette, isPad: Bool) -> UIStackView {
        let row = UIStackView()
        row.axis = .horizontal
        row.spacing = isPad ? 7 : 5
        row.distribution = .fill

        let symBtn = makeSpecialButton(
            title: drawerMode == .symbols ? "ABC" : "?123",
            systemName: nil,
            palette: palette,
            role: .functionKey,
            fixedWidth: isPad ? 68 : 54
        ) { [weak self] in
            guard let self else { return }
            self.drawerMode = (self.drawerMode == .symbols) ? .letters : .symbols
            self.rebuildAll()
        }
        row.addArrangedSubview(symBtn)

        if needsInputModeSwitchKey {
            let globeBtn = makeSpecialButton(
                title: nil,
                systemName: "globe",
                palette: palette,
                role: .functionKey,
                fixedWidth: isPad ? 52 : 40
            ) { [weak self] in
                self?.advanceToNextInputMode()
            }
            row.addArrangedSubview(globeBtn)
        }

        let spaceBtn = LiquidGlassKeyButton(type: .system)
        spaceBtn.palette = palette
        spaceBtn.orbRole = .normal
        spaceBtn.setTitle(lang.layoutLabel, for: .normal)
        spaceBtn.setTitleColor(UIColor(palette.textSecondary), for: .normal)
        spaceBtn.titleLabel?.font = .systemFont(ofSize: 13.5, weight: .medium)
        spaceBtn.addTarget(self, action: #selector(handleSpaceTap), for: .touchUpInside)
        let spaceLongPress = UILongPressGestureRecognizer(target: self, action: #selector(handleSpaceLongPress(_:)))
        spaceBtn.addGestureRecognizer(spaceLongPress)
        let cursorPan = UIPanGestureRecognizer(target: self, action: #selector(handleSpaceCursorPan(_:)))
        spaceBtn.addGestureRecognizer(cursorPan)
        row.addArrangedSubview(spaceBtn)

        let periodBtn = makeSpecialButton(
            title: ".",
            systemName: nil,
            palette: palette,
            role: .functionKey,
            fixedWidth: isPad ? 50 : 38
        ) { [weak self] in
            self?.commitCharacter(".")
        }
        row.addArrangedSubview(periodBtn)

        let returnBtn = makeSpecialButton(
            title: nil,
            systemName: "return",
            palette: palette,
            role: .accentAction,
            fixedWidth: isPad ? 76 : 58
        ) { [weak self] in
            self?.commitCharacter("\n")
        }
        row.addArrangedSubview(returnBtn)

        if isPad && !needsInputModeSwitchKey {
            let dismissBtn = makeSpecialButton(
                title: nil,
                systemName: "keyboard.chevron.compact.down",
                palette: palette,
                role: .functionKey,
                fixedWidth: 52
            ) { [weak self] in
                self?.dismissKeyboard()
            }
            row.addArrangedSubview(dismissBtn)
        }

        return row
    }

    private func makeSpecialButton(
        title: String?,
        systemName: String?,
        palette: GboardPalette,
        role: KeyboardOrbRole,
        fixedWidth: CGFloat? = nil,
        widthMultiplier: CGFloat? = nil,
        action: @escaping () -> Void
    ) -> LiquidGlassKeyButton {
        let btn = LiquidGlassKeyButton(type: .system)
        btn.palette = palette
        btn.orbRole = role
        btn.tintColor = UIColor(role == .accentAction ? palette.ctaForeground : palette.textPrimary)
        if let systemName {
            btn.setImage(UIImage(systemName: systemName), for: .normal)
        }
        if let title {
            btn.setTitle(title, for: .normal)
            btn.setTitleColor(UIColor(role == .accentAction ? palette.ctaForeground : palette.textPrimary), for: .normal)
            btn.titleLabel?.font = .systemFont(ofSize: 14, weight: .semibold)
        }
