package br.com.ricarlo.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.Assert

inline fun <reified T : Any> LiveData<T>.test(): Observer<T> {
    Assert.assertFalse(hasObservers())
    val observer = mockk<Observer<T>> { every { onChanged(any()) } just Runs }
    observeForever(observer)
    return observer
}
