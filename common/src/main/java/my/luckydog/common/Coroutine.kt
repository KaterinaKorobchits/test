package my.luckydog.common

import kotlinx.coroutines.*

suspend fun <T> withIOContext(
    block: suspend CoroutineScope.() -> T
): T = withContext(Dispatchers.IO, block)

suspend fun <T> withDefaultContext(
    block: suspend CoroutineScope.() -> T
): T = withContext(Dispatchers.Default, block)

fun <T> CoroutineScope.asyncIO(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> = async(Dispatchers.IO, start, block)

fun <T> CoroutineScope.asyncDefault(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> = async(Dispatchers.Default, start, block)

suspend fun <T> withTimeoutOrNullDefaultContext(
    timeMillis: Long,
    block: suspend CoroutineScope.() -> T
): T? = withDefaultContext { withTimeoutOrNull(timeMillis, block) }

suspend fun <T> withTimeoutDefaultContext(
    timeMillis: Long,
    block: suspend CoroutineScope.() -> T
): T? = withDefaultContext { withTimeout(timeMillis, block) }