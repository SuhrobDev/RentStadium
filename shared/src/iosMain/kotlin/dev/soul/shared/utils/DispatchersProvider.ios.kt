package dev.soul.shared.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class IosDispatcher : Dispatcher {
    override val io: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}

actual fun provideDispatcher(): Dispatcher = IosDispatcher()