package dev.soul.shared.utils

expect object Logger {
    fun log(tag:String,message: String)
    fun logError(tag:String,message: String)
    fun logWarning(tag:String,message: String)
    fun logInfo(tag:String,message: String)
    fun logDebug(tag:String,message: String)
    fun logVerbose(tag:String,message: String)
}
