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
        XCTAssertTrue(additionalRow.exists)
        additionalRow.tap()

        XCTAssertTrue(app.buttons["row_choose_ui"].waitForExistence(timeout: 3))
        app.buttons["row_choose_ui"].tap()
        app.buttons["choose_ui_liquid_glass"].tap()
        app.buttons["choose_ui_classic"].tap()
        app.navigationBars.buttons.element(boundBy: 0).tap()

