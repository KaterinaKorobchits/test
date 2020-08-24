package my.luckydog.common.extensions

import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*

fun checkMainThread() = check(Looper.myLooper() == Looper.getMainLooper()) {
    "Expected to be called on the main thread but was " + Thread.currentThread().name
}

fun TextView.textChanges() = callbackFlow {

    val textChangedListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable) = Unit
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            offer(s.toString())
        }
    }

    addTextChangedListener(textChangedListener)
    awaitClose { removeTextChangedListener(textChangedListener) }
}.conflate()

fun View.clicks() = callbackFlow {
    setOnClickListener { offer(Unit) }
    awaitClose { setOnClickListener(null) }
}.conflate()

fun CompoundButton.checkedChanges() = callbackFlow {
    setOnCheckedChangeListener { _, isChecked -> offer(isChecked) }
    awaitClose { setOnCheckedChangeListener(null) }
}.conflate()

fun EditText.editorActions(handled: (Int) -> Boolean = { true }) = callbackFlow {
    val listener = TextView.OnEditorActionListener { _, actionId, _ ->
        if (handled(actionId)) {
            offer(actionId)
            true
        } else false
    }
    setOnEditorActionListener(listener)
    awaitClose { setOnEditorActionListener(null) }
}.conflate()


fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }
    return flow {
        var lastTime = 0L
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                lastTime = currentTime
                emit(value)
            }
        }
    }
}

suspend fun <E> ReceiveChannel<E>.throttle(
    periodMillis: Long = 400
): ReceiveChannel<E> = coroutineScope {
    produce {
        var nextTime = 0L
        consumeEach {
            val currentTime = System.currentTimeMillis()
            if (currentTime >= nextTime) {
                nextTime = currentTime + periodMillis
                send(it)
            }
        }
    }
}