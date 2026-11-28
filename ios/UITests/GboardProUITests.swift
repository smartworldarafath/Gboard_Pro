import XCTest

final class GboardProUITests: XCTestCase {

    override func setUpWithError() throws {
        continueAfterFailure = false
    }

    func testNavigateCoreAndBetaSettingsOnVirtualDevice() throws {
        let app = XCUIApplication()
        app.launch()

        XCTAssertTrue(app.staticTexts["Gboard Pro Settings"].waitForExistence(timeout: 5))

        // Navigate into Additional -> Choose UI -> Switch to 3D Liquid Glass & back to Classic
        let additionalRow = app.buttons["row_additional"]
        if !additionalRow.isHittable {
            app.swipeUp()
        }
        XCTAssertTrue(additionalRow.waitForExistence(timeout: 3))
        additionalRow.tap()

        XCTAssertTrue(app.buttons["row_choose_ui"].waitForExistence(timeout: 3))
        app.buttons["row_choose_ui"].tap()
        app.buttons["choose_ui_liquid_glass"].tap()
        app.buttons["choose_ui_classic"].tap()
        app.navigationBars.buttons.element(boundBy: 0).tap()

        // Notification screen (3 toggles)
        app.buttons["row_notification"].tap()
        XCTAssertTrue(app.staticTexts["NOTIFICATION PREFERENCES"].waitForExistence(timeout: 3))
        app.navigationBars.buttons.element(boundBy: 0).tap()

        // Beta Program -> Beta version testing & New features (Beta)
        app.buttons["row_beta_program"].tap()
        XCTAssertTrue(app.buttons["row_beta_version_testing"].waitForExistence(timeout: 3))
        app.buttons["row_beta_version_testing"].tap()
        XCTAssertTrue(app.staticTexts["BETA TESTING NOTICE"].waitForExistence(timeout: 3))
        app.navigationBars.buttons.element(boundBy: 0).tap()

        app.buttons["row_new_features_(beta)"].tap()
        let betaSlider = app.switches["toggle_enable_new_features_(beta)"]
        XCTAssertTrue(betaSlider.waitForExistence(timeout: 3))
        betaSlider.tap()
        XCTAssertTrue(app.staticTexts["CURRENTLY TESTING BETA FEATURES"].waitForExistence(timeout: 3))
    }

    func testLiveKeyboardPreviewTyping() throws {
        let app = XCUIApplication()
        app.launch()

        app.buttons["btn_preview_keyboard"].tap()
        XCTAssertTrue(app.staticTexts["LIVE IPHONE / IPAD KEYBOARD SANDBOX"].waitForExistence(timeout: 4))
    }

    func testAllLiquidGlassThemesApplyCleanly() throws {
        let app = XCUIApplication()
        app.launch()

        let themeRow = app.buttons["row_theme"]
        XCTAssertTrue(themeRow.waitForExistence(timeout: 4))
        themeRow.tap()

        let styleRow = app.buttons["row_style"]
        XCTAssertTrue(styleRow.waitForExistence(timeout: 3))
        styleRow.tap()

        for id in ["blush_lilac", "cotton_candy", "sunset_peach", "fresh_mint", "ocean", "default"] {
            let btn = app.buttons["theme_option_\(id)"]
            if !btn.isHittable {
                app.swipeUp()
            }
            XCTAssertTrue(btn.waitForExistence(timeout: 3))
            btn.tap()
        }
    }
}
