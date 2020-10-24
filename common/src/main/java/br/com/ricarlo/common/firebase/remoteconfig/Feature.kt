package br.com.ricarlo.common.firebase.remoteconfig

import androidx.annotation.StringDef

@StringDef(
        Feature.WELCOME_MESSAGE,
        Feature.WELCOME_MESSAGE,
        Feature.MIN_VERSION
)
@Retention(AnnotationRetention.SOURCE)
annotation class Feature {
    companion object {
        const val WELCOME_MESSAGE = "welcome_message"
        const val MIN_VERSION = "mim_version"
        const val IN_APP_REVIEW = "in_app_review"
    }
}
