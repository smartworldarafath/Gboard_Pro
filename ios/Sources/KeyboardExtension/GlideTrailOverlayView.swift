import UIKit

/// Renders fingertips glide typing trail cleanly using CoreAnimation CAShapeLayer.
final class GlideTrailOverlayView: UIView {
    private let trailLayer = CAShapeLayer()
    private var points: [CGPoint] = []

    var trailColor: UIColor = .systemBlue {
        didSet { trailLayer.strokeColor = trailColor.withAlphaComponent(0.72).cgColor }
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        isUserInteractionEnabled = false
        backgroundColor = .clear
        trailLayer.fillColor = UIColor.clear.cgColor
        trailLayer.lineWidth = 4.5
        trailLayer.lineCap = .round
        trailLayer.lineJoin = .round
        layer.addSublayer(trailLayer)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

