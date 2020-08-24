package my.luckydog.rx

import io.reactivex.Observable
import org.junit.Test

class ObservableTest {

    @Test
    fun observable() {
        Observable.just(1, 2, 3, 1)
            .subscribe()
    }

    @Test
    fun `observable with one consumer`() {
        Observable.just(1, 2, 3, 1)
            .map { it / (it - 3) }
            .subscribe {
                println("subscribe + $it")
            }
    }

    @Test
    fun `observable with two consumers`() {
        Observable.just(1, 2, 3, 1)
            .doOnSubscribe { println("doOnSubscribe + $it") }
            .doOnSubscribe { println("doOnSubscribe + $it") }
            .map { it / (it - 3) }
            .doOnSubscribe { println("doOnSubscribe + $it") }
            .subscribe({
                println("subscribe + $it")
            }, {
                println("subscribe + $it")
            })
    }

    @Test
    fun adf(){
        val observer = Observable.fromCallable { 1 }
            .map { println("start rest 1")}
            .share()


        observer.flatMap{ Observable.just(2)}.subscribe{println("complete rest 2")}
        observer.subscribe{println("log update ui")}
        /*Observable.just(1,2)
            .log("just")
            .map{it + 1}
            .log("map")
            .flatMap { Observable.just(it + 1, it+3).log("innerjust") }
            .log("flatmap")
            .subscribe()

        Observable.never<>()*/
        Unit
        val b = 1 as Nothing
    }

    @Test
    fun `observable do operators`() {
        Observable.just(1, 2, 3)
            .doOnSubscribe { println("doOnSubscribe") }

            .doOnEach { println("doOnEach  + $it ") }
            .doOnNext { println("doOnNext  + $it") }
            .doAfterNext { println("doAfterNext  + $it") }

            .doOnComplete { println("doOnComplete") }
            .doOnTerminate { println("doOnTerminate ") }
            .doFinally { println("doFinally ") }
            .doAfterTerminate { println("doAfterTerminate") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable do operators with error`() {
        Observable.just(1, 2, 3)
            .doOnSubscribe { println("doOnSubscribe") }

            .doOnEach { println("doOnEach  + $it ") }
            .doOnNext {
                println("doOnNext  + $it")
                if (it == 2) throw Exception()
            }
            .doAfterNext { println("doAfterNext  + $it") }

            .doOnError { println("doOnError") }
            .doOnTerminate { println("doOnTerminate ") }
            .doFinally { println("doFinally ") }
            .doAfterTerminate { println("doAfterTerminate") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable onErrorReturn`() {
        Observable.just(1, 2, 3, 1)
            .map { it / (3 - it) }
            .onErrorReturn {
                if (it is ArithmeticException) -2
                else -1
            }
            .doOnError { println("doOnError") }
            .doOnComplete { println("doOnComplete") }
            .doOnTerminate { println("doOnTerminate ") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable onErrorReturnItem`() {
        Observable.just(1, 2, 3, 1)
            .map { it / (3 - it) }
            .onErrorReturnItem(-1)
            .doOnError { println("doOnError") }
            .doOnComplete { println("doOnComplete") }
            .doOnTerminate { println("doOnTerminate ") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable onErrorResumeNext`() {
        Observable.just(1, 2, 3, 1)
            .map { it / (3 - it) }
            .onErrorResumeNext(Observable.range(10, 3))
            .doOnError { println("doOnError") }
            .doOnComplete { println("doOnComplete") }
            .doOnTerminate { println("doOnTerminate ") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable elementAtOrError success`() {
        Observable.just(1, 2, 3, 1)
            .map { it / (3 - it) }
            .elementAtOrError(1)

            .doOnError { println("doOnError") }
            .doOnSuccess { println("doOnSuccess") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable elementAtOrError error`() {
        Observable.just(1, 2, 3, 1)
            .map { it / (3 - it) }
            .elementAtOrError(2)

            .doOnError { println("doOnError") }
            .doOnSuccess { println("doOnSuccess") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable firstOrError success`() {
        Observable.just(1, 2, 3, 1)
            .map { it / (3 - it) }
            .firstOrError()
            .doOnSuccess { println("doOnSuccess") }
            .doOnError { println("doOnError") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable firstOrError error`() {
        Observable.just(3, 2, 3, 1)
            .map { it / (3 - it) }
            .firstOrError()
            .doOnSuccess { println("doOnSuccess") }
            .doOnError { println("doOnError") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable lastOrError success`() {
        Observable.just(1, 2, 1)
            .map { it / (3 - it) }
            .lastOrError()
            .doOnSuccess { println("doOnSuccess") }
            .doOnError { println("doOnError") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable lastOrError error`() {
        Observable.just(1, 2, 3)
            .map { it / (3 - it) }
            .lastOrError()
            .doOnSuccess { println("doOnSuccess") }
            .doOnError { println("doOnError") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable singleOrError success`() {
        Observable.just(1)
            .singleOrError()
            .doOnSuccess { println("doOnSuccess") }
            .doOnError { println("doOnError") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }

    @Test
    fun `observable singleOrError error`() {
        Observable.just(1, 2, 3)
            .singleOrError()
            .doOnSuccess { println("doOnSuccess") }
            .doOnError { println("doOnError") }

            .subscribe(
                {
                    println("subscribe + $it")
                }, {
                    println("subscribe + $it")
                }
            )
    }
}

fun <T> Observable<T>.log(text: String) : Observable<T> {
    return this.doOnSubscribe { println("onSUbscribe $text")}
        .doOnNext {println("onNext $text $it")}
        .doOnError {println("onError $text ${it.message}")}
        .doOnComplete {println("onComplete $text")}
}