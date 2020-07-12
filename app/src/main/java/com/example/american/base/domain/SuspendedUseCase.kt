package com.example.american.base.domain

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class SuspendedUseCase<in P, R> : CoroutineScope {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, t ->
        run {
            t.printStackTrace()
        }
    }

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + coroutineExceptionHandler

    val errorMessage: String
        get() = onErrorMessage()

    operator fun invoke(params: P, ioDispatcher: CoroutineDispatcher = Dispatchers.IO, onResult: (R) -> Unit = {}) =
            launch(coroutineContext) {
                val job = async(ioDispatcher) {
                    run(params)
                }
                onResult(job.await())
            }

    abstract suspend fun run(params: P): R
    abstract fun onErrorMessage(): String
}
