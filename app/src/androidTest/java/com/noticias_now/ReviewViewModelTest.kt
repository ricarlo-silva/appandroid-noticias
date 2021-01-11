package com.noticias_now

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.google.android.play.core.review.testing.FakeReviewManager
import org.junit.Test
import org.junit.Before
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.ricarlo.common.inapp.review.ReviewViewModel
import org.junit.Rule
import org.junit.Assert.*

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReviewViewModelTest {

    private lateinit var fakeReviewManager: FakeReviewManager

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ReviewViewModel
    //    private val contextMock = mockk<Context>(relaxed = true)

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        fakeReviewManager = FakeReviewManager(context)
//        viewModel = ReviewViewModel(fakeReviewManager)
    }

    @Test
    fun testNull() {
//        viewModel.requestReviewFlow()
//        assertNotNull(viewModel.reviewFlow.getOrAwaitValue())
//        assertTrue(viewModel.reviewFlow.hasObservers())

    }
}