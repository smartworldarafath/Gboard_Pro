import Foundation
#if canImport(Combine)
import Combine
#endif

public struct GitHubReleaseAsset: Codable, Equatable {
    public let name: String
    public let size: Int64
    public let browserDownloadUrl: String

    enum CodingKeys: String, CodingKey {
        case name
        case size
        case browserDownloadUrl = "browser_download_url"
    }
}

public struct GitHubReleaseDTO: Codable, Equatable {
    public let tagName: String
    public let name: String
    public let body: String
    public let draft: Bool
    public let prerelease: Bool
    public let publishedAt: String
    public let htmlUrl: String
    public let assets: [GitHubReleaseAsset]

    enum CodingKeys: String, CodingKey {
        case tagName = "tag_name"
        case name
        case body
        case draft
        case prerelease
        case publishedAt = "published_at"
        case htmlUrl = "html_url"
        case assets
    }
}

public struct BetaFeatureItem: Identifiable, Equatable {
    public let id = UUID()
    public let title: String
    public let summary: String
}

public final class ReleaseAndBetaService: ObservableObject {
    public static let shared = ReleaseAndBetaService()

    public enum BetaState: Equatable {
        case noActiveBeta
        case checking
        case available(version: String, fileName: String, sizeBytes: Int64, downloadUrl: String, htmlUrl: String, bullets: [String])
        case downloading(version: String, fileName: String, progress: Double, mbDownloaded: Double, mbTotal: Double)
        case completed(version: String, localFileURL: URL)
        case error(String)
    }

    @Published public var latestRelease: GitHubReleaseDTO?
    @Published public var isCheckingUpdate = false
    @Published public var updateStatusMessage = "Up to date"
    @Published public var betaState: BetaState = .noActiveBeta
    @Published public var testingFeatureBullets: [String] = [
        "No active beta testing build features are listed right now."
    ]
    @Published public var activeExperimentalBetaFeatures: [BetaFeatureItem] = []

