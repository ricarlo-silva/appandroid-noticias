package br.com.ricarlo.common.di

import br.com.ricarlo.common.firebase.remoteconfig.di.FirebaseRemoteConfigModule
import br.com.ricarlo.common.inapp.review.di.InAppReviewModule
import br.com.ricarlo.common.inapp.update.di.InAppUpdateModule
import br.com.ricarlo.common.util.coroutines.CoroutinesDispatcherProvider
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import br.com.ricarlo.common.util.resources.di.ResourcesModule
import org.koin.dsl.module

object CommonModule {
    val modules = FirebaseRemoteConfigModule.module +
        InAppReviewModule.module +
        InAppUpdateModule.module +
        ResourcesModule.modules +
        module {
            single<ICoroutinesDispatcherProvider> {
                CoroutinesDispatcherProvider()
            }
        }
}
