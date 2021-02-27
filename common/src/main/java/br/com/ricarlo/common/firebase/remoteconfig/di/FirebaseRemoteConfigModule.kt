package br.com.ricarlo.common.firebase.remoteconfig.di

import br.com.ricarlo.common.R
import br.com.ricarlo.common.firebase.remoteconfig.FirebaseRemoteConfigManager
import br.com.ricarlo.common.firebase.remoteconfig.FirebaseRemoteConfigProvider
import br.com.ricarlo.common.firebase.remoteconfig.IFirebaseRemoteConfigManager
import org.koin.dsl.module

internal object FirebaseRemoteConfigModule {
    val module = module {

        single {
            FirebaseRemoteConfigProvider(
                defaultValues = R.xml.remote_config_defaults,
                fetchIntervalInSeconds = 3600
            ).build()
        }

        single<IFirebaseRemoteConfigManager> {
            FirebaseRemoteConfigManager(firebaseRemoteConfig = get())
        }
    }
}
