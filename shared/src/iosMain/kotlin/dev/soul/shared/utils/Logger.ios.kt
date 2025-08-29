package dev.soul.shared.utils

actual object Logger {
    actual fun log(tag: String, message: String) {
        print("[$tag] $message")
    }

    actual fun logError(tag: String, message: String) {
        print("[$tag] $message")
    }

    actual fun logWarning(tag: String, message: String) {
        print("[$tag] $message")
    }

    actual fun logInfo(tag: String, message: String) {
        print("[$tag] $message")
    }

    actual fun logDebug(tag: String, message: String) {
        print("[$tag] $message")
    }

    actual fun logVerbose(tag: String, message: String) {
        print("[$tag] $message")
    }
}