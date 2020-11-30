package br.com.ricarlo.common.inapp.review.di

import br.com.ricarlo.common.inapp.review.ReviewViewModel
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal object InAppReviewModule {
    val module = module {

        single<ReviewManager> {
            ReviewManagerFactory.create(get())
        }

        single {
            ReviewViewModel(get(), get())
        }
    }
}