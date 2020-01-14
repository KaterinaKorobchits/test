package my.luckydog.subjects

import io.reactivex.observers.TestObserver
import io.reactivex.subjects.BehaviorSubject
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class BehaviorSubjectTest {

    private val subject = BehaviorSubject.create<Int>()

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
        observer.assertValueCount(1)
        observer.assertValue(1)
    }

    @Test
    fun `subscribe observer after two onNext`() {
        val observer = TestObserver.create<Int>()
        subject.onNext(1)
        subject.onNext(2)
        subject.subscribe(observer)

        assertTrue(subject.hasObservers())
        observer.assertSubscribed()
        observer.assertValueCount(1)
        observer.assertValue(2)
    }

    @Test
    fun `subscribe observer before onNext`() {
        val observer = TestObserver.create<Int>()
        subject.subscribe(observer)
        subject.onNext(1)

        assertTrue(subject.hasObservers())
        observer.assertSubscribed()
        observer.assertValueCount(1)
        observer.assertValue(1)
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
    fun `onNext and complete with observer cancel`() {
        val observer = TestObserver.create<Int>()
        subject.subscribe(observer)
        subject.onNext(1)
        subject.onNext(2)
        observer.cancel()
        subject.onNext(3)
        subject.onComplete()

        assertTrue(subject.hasComplete())
        assertFalse(subject.hasObservers())
        observer.assertSubscribed()
        observer.assertValueCount(2)
        observer.assertValues(1, 2)
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
        observer1.assertValueCount(3)
        observer1.assertValues(1, 2, 3)
        observer1.assertComplete()
        observer2.assertSubscribed()
        observer2.assertValueCount(2)
        observer2.assertValues(2, 3)
        observer2.assertComplete()
    }
}