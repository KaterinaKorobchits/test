package my.luckydog.presentation.core

import android.util.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

private fun testBuffer() {
    val observable = Observable.interval(100, TimeUnit.MILLISECONDS)
        .takeWhile { it != 10L }
        //.buffer(3) // [0, 1, 2] [3, 4, 5] [6, 7, 8] [9]
        //.buffer(3,5) // [0, 1, 2] [5, 6, 7]
        //.buffer(300,500,TimeUnit.MILLISECONDS) //[0, 1] [4, 5, 6, 7] [9]
        //.buffer(400, TimeUnit.MILLISECONDS) // [0, 1, 2] [3, 4, 5, 6] [7, 8, 9]
        .buffer(400, TimeUnit.MILLISECONDS, 2) //[0, 1] [2] [3, 4] [5, 6] [] [7, 8] [9]

        .subscribe({
            Log.e("aaa", "subscribe $it")
        }, {
            Log.e("aaa", it.toString())
        })
}