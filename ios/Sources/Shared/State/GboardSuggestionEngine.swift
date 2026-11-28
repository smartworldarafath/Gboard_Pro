import Foundation

/// Lightweight predictive text & glide word matcher safe under iOS Keyboard Extension memory caps.
public enum GboardSuggestionEngine {
    private static let lexicon: [String] = [
        "the", "that", "there", "their", "they", "this", "these", "those", "thank", "thanks",
        "hello", "hey", "hi", "how", "have", "here", "help", "happy", "home", "good",
        "great", "gboard", "going", "goodness", "galaxy", "iphone", "ipad", "ios",
        "android", "awesome", "already", "always", "about", "amazing", "awesome",
        "what", "when", "where", "which", "who", "why", "will", "with", "would", "work",
        "working", "wonderful", "yes", "yeah", "you", "your", "yours", "today", "tomorrow",
        "tonight", "time", "take", "test", "testing", "keyboard", "liquid", "glass",
        "classic", "material", "dark", "amoled", "theme", "beta", "pro", "message",
        "please", "perfect", "okay", "sure", "nice", "cool", "bro", "love", "need", "make"
    ]

