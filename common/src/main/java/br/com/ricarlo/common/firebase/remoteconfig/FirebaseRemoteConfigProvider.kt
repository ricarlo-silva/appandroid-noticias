package br.com.ricarlo.common.firebase.remoteconfig

import androidx.annotation.XmlRes
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

internal class FirebaseRemoteConfigProvider(
    @XmlRes private val defaultValues: Int,
    private val fetchIntervalInSeconds: Long = 3600
) {

    fun build() = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(
            remoteConfigSettings {
//                minimumFetchIntervalInSeconds = fetchIntervalInSeconds
            }
        )
        setDefaultsAsync(defaultValues)
    }
}
