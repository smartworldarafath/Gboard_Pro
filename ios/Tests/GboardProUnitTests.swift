import XCTest
@testable import GboardProApp

final class GboardProUnitTests: XCTestCase {

    func testDefaultUiModeIsClassicMaterialUI() {
        let suiteName = "test.suite.\(UUID().uuidString)"
        let defaults = UserDefaults(suiteName: suiteName)!
        let store = GboardSettingsStore(defaults: defaults)
        XCTAssertEqual(store.uiStyleMode, .classic)
        XCTAssertFalse(store.amoledBlackEnabled)
        XCTAssertFalse(store.betaNewFeaturesSliderEnabled)
        XCTAssertTrue(store.notifyAppUpdatesAlert)
        XCTAssertTrue(store.notifyBetaApkAlert)
        XCTAssertTrue(store.notifyBetaFeatures)
        defaults.removePersistentDomain(forName: suiteName)
    }

    func testPaletteResolvesDarkAndAmoledWaterDropContrast() {
        let classicLight = GboardPalette.resolve(style: .classic, theme: .systemDefault, isDark: false, isAmoled: false)
        XCTAssertFalse(classicLight.isLiquidGlass)
        XCTAssertFalse(classicLight.isDark)

        let darkGlass = GboardPalette.resolve(style: .liquidGlass, theme: .systemDefault, isDark: true, isAmoled: false)
        XCTAssertTrue(darkGlass.isLiquidGlass)
        XCTAssertTrue(darkGlass.isDark)
        XCTAssertNotEqual(darkGlass.orbBodyTopHex, darkGlass.orbBodyMidHex)

        let amoledGlass = GboardPalette.resolve(style: .liquidGlass, theme: .systemDefault, isDark: true, isAmoled: true)
        XCTAssertTrue(amoledGlass.isAmoled)
        XCTAssertEqual(amoledGlass.keyboardBackgroundHex, 0x000000)
    }

    func testStripEmojisRemovesAllEmojiCharactersFromBetaBullets() {
        let raw = "- \u{1F680} Improved speed \u{2728} for Dark Mode \u{1F525}"
        let bullets = ReleaseAndBetaService.extractBulletFeaturesWithoutEmojis(from: raw)
        XCTAssertEqual(bullets, ["Improved speed for Dark Mode"])
    }
