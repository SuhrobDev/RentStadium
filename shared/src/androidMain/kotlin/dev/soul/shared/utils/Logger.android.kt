package dev.soul.shared.utils

import android.util.Log

actual object Logger {
    actual fun log(tag: String, message: String) {
        Log.d(tag, message)
    }

    actual fun logError(tag: String, message: String) {
        Log.e(tag, message)
    }

    actual fun logWarning(tag: String, message: String) {
        Log.w(tag, message)
    }

    actual fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
    }

    actual fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }

    actual fun logVerbose(tag: String, message: String) {
        Log.v(tag, message)
    }
}