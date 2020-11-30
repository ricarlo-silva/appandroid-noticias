package br.com.ricarlo.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    afterObserve.invoke()

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

/**
 * Observes a [LiveData] until the `block` is done executing.
 */
//fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
//    val observer = Observer<T> { }
//    try {
//        observeForever(observer)
//        block()
//    } finally {
//        removeObserver(observer)
//    }
//}

inline fun <reified T : Any> LiveData<T>.observeForTesting(block: (states: List<T>) -> Unit) {
    val observer = mockk<Observer<T>> { every { onChanged(any()) } just Runs }

    try {
        observeForever(observer)
        val slots = mutableListOf<T>()
        verify {
            observer.onChanged(capture(slots))
        }
        block(slots)
    } finally {
        removeObserver(observer)
    }
}

//inline fun <reified T : Any> LiveData<T>.observeForTesting(block: () -> Unit) {
//
//    val observer = mockk<Observer<T>>()
//
//    //create slot
//    val slot = slot<T>()
//
//    //create list to store values
//    val list = arrayListOf<T>()
//
//    //start observing
////    viewModel.postStateWithSuspend.observeForever(observer)
//
//
//    //capture value on every call
//    every { observer.onChanged(capture(slot)) } answers {
//
//        //store captured value
//        list.add(slot.captured)
//    }
//
//    try {
//        observeForever(observer)
//        val slots = mutableListOf<T>()
//        verify {
//            observer.onChanged(capture(slots))
//        }
//        block()
//    } finally {
//        removeObserver(observer)
//    }
//}


class LiveDataTestObserver<T> constructor(
        private val liveData: LiveData<T>
) : Observer<T> {

    init {
        liveData.observeForever(this)
    }

    private val testValues = mutableListOf<T>()

    override fun onChanged(t: T) {
        if (t != null) testValues.add(t)
    }

    fun assertNoValues(): LiveDataTestObserver<T> {
        if (testValues.isNotEmpty()) throw AssertionError(
                "Assertion error with actual size ${testValues.size}"
        )
        return this
    }

    fun assertValueCount(count: Int): LiveDataTestObserver<T> {
        if (count < 0) throw AssertionError(
                "Assertion error! value count cannot be smaller than zero"
        )
        if (count != testValues.size) throw AssertionError(
                "Assertion error! with expected $count while actual ${testValues.size}"
        )
        return this
    }

    fun assertValue(vararg predicates: T): LiveDataTestObserver<T> {
        if (!testValues.containsAll(predicates.asList())) throw AssertionError("Assertion error!")
        return this
    }

    fun assertValue(predicate: (List<T>) -> Boolean): LiveDataTestObserver<T> {
        predicate(testValues)
        return this
    }

    fun values(predicate: (List<T>) -> Unit): LiveDataTestObserver<T> {
        predicate(testValues)
        return this
    }

    fun values(): List<T> {
        return testValues
    }

    /**
     * Removes this observer from the [LiveData] which was observing
     */
    fun dispose() {
        liveData.removeObserver(this)
    }

    /**
     * Clears data available in this observer and removes this observer from the [LiveData] which was observing
     */
    fun clear() {
        testValues.clear()
        dispose()
    }
}

fun <T> LiveData<T>.test(): LiveDataTestObserver<T> {

    val testObserver = LiveDataTestObserver(this)

    // Remove this testObserver that is added in init block of TestObserver, and clears previous data
    testObserver.clear()
    observeForever(testObserver)

    return testObserver
}
