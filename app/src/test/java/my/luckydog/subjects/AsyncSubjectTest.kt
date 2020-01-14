package my.luckydog.subjects

import io.reactivex.observers.TestObserver
import io.reactivex.subjects.AsyncSubject
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class AsyncSubjectTest {

    private val subject = AsyncSubject.create<Int>()

    @Test
    fun `no observers`() {
        assertFalse(subject.hasObservers())
    }

    @Test
    fun `on Complete`() {
        subject.onNext(1)
        subject.onComplete()
        assertTrue(subject.hasComplete())
        assertFalse(subject.hasObservers())
    }

    @Test
    fun `has observer`() {
        val observer = TestObserver.create<Int>()
        subject.subscribe(observer)
        assertTrue(subject.hasObservers())
    }

    @Test
    fun `subscribe observer after onNext`() {
        val observer = TestObserver.create<Int>()
        subject.onNext(1)
        subject.subscribe(observer)

        assertTrue(subject.hasObservers())
        observer.assertSubscribed()
        observer.assertNoValues()
    }

    @Test
    fun `subscribe observer before onNext`() {
        val observer = TestObserver.create<Int>()
        subject.subscribe(observer)
        subject.onNext(1)

        assertTrue(subject.hasObservers())
        observer.assertSubscribed()
        observer.assertNoValues()
    }

    @Test
    fun `onNext and complete with observer`() {
        val observer = TestObserver.create<Int>()
        subject.subscribe(observer)
        subject.onNext(1)
        subject.onComplete()

        assertTrue(subject.hasComplete())
        assertFalse(subject.hasObservers())
        observer.assertSubscribed()
        observer.assertValueCount(1)
        observer.assertValue(1)
        observer.assertComplete()
    }

    @Test
    fun `two onNext and complete with observer`() {
        val observer = TestObserver.create<Int>()
        subject.subscribe(observer)
        subject.onNext(1)
        subject.onNext(2)
        subject.onComplete()

        assertTrue(subject.hasComplete())
        assertFalse(subject.hasObservers())
        observer.assertSubscribed()
        observer.assertValueCount(1)
        observer.assertValue(2)
        observer.assertComplete()
    }

    @Test
    fun `onNext and complete with observer cancel`() {
        val observer = TestObserver.create<Int>()
        subject.subscribe(observer)
        subject.onNext(1)
        subject.onNext(1)
        observer.cancel()
        subject.onNext(1)
        subject.onComplete()

        assertTrue(subject.hasComplete())
        assertFalse(subject.hasObservers())
        observer.assertSubscribed()
        observer.assertNoValues()
        observer.assertNotComplete()
    }

    @Test
    fun `onNext and complete with observers`() {
        val observer1 = TestObserver.create<Int>()
        val observer2 = TestObserver.create<Int>()
        subject.subscribe(observer1)
        subject.onNext(1)
        subject.onNext(2)
        subject.subscribe(observer2)
        subject.onNext(3)
        subject.onComplete()

        assertTrue(subject.hasComplete())
        assertFalse(subject.hasObservers())
        observer1.assertSubscribed()
        observer1.assertValueCount(1)
        observer1.assertValue(3)
        observer1.assertComplete()
        observer2.assertSubscribed()
        observer2.assertValueCount(1)
        observer2.assertValues(3)
        observer2.assertComplete()
    }
}