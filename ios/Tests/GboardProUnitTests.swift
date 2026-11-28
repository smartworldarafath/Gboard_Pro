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
