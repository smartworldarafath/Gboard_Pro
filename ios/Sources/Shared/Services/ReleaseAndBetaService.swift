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

