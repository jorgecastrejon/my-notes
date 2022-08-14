package org.jcastrejon.arch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {
    val io: CoroutineContext
    val default: CoroutineContext
    val main: CoroutineContext
}

class DefaultDispatcherProvider : DispatcherProvider {
    override val io: CoroutineContext by lazy { Dispatchers.IO }
    override val default: CoroutineContext by lazy { Dispatchers.Default }
    override val main: MainCoroutineDispatcher by lazy { Dispatchers.Main }
}