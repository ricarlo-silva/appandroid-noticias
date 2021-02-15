package com.ricarlo.storage

import androidx.datastore.preferences.createDataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DataStoreTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val dataStore = appContext.createDataStore(name = "test")

    @Before
    fun setUp() {
        runBlocking { dataStore.clear() }
    }

    @Test
    fun save() {
        val key1 = "key_1"
        val key2 = "key_2"
        val key3 = "key_3"
        val key4 = "key_4"
        val key5 = "key_5"
        val key6 = "key_6"

        runBlocking {
            dataStore.save(key1, 1)
            dataStore.save(key2, "I am")
            dataStore.save(key3, true)
            dataStore.save(key4, 2F)
            dataStore.save(key5, 3L)
            dataStore.save(key6, 4.toDouble())

            val data1 = dataStore.get<Int>(key1)
            assertEquals(1, data1)

            val data2 = dataStore.get<String>(key2)
            assertEquals("I am", data2)

            val data3 = dataStore.get<Boolean>(key3)
            assertEquals(true, data3)

            val data4 = dataStore.get<Float>(key4)
            assertEquals(2F, data4)

            val data5 = dataStore.get<Long>(key5)
            assertEquals(3L, data5)

            val data6 = dataStore.get<Double>(key6)
            assertEquals(4.toDouble(), data6)
        }
    }

    @Test
    fun delete() {
        val key1 = "key_1"
        runBlocking {
            dataStore.save(key1, "I am")

            dataStore.delete<String>(key1)

            val has = dataStore.hasKey<String>(key1)
            assertFalse(has)
        }
    }

    @Test
    fun clear() {
        val key1 = "key_1"
        val key2 = "key_2"

        runBlocking {
            dataStore.save(key1, "I am")
            dataStore.save(key2, 9.00)

            dataStore.clear()

            val data1 = dataStore.hasKey<String>(key1)
            assertFalse(data1)

            val data2 = dataStore.hasKey<Double>(key2)
            assertFalse(data2)
        }
    }

}