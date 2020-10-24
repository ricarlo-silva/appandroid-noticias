package br.com.ricarlo.common.di

import br.com.ricarlo.common.firebase.remoteconfig.di.FirebaseRemoteConfigModule
import br.com.ricarlo.common.inapp.review.di.InAppReviewModule
import br.com.ricarlo.common.inapp.update.di.InAppUpdateModule

object CommonModule {
    val modules = FirebaseRemoteConfigModule.module +
            InAppReviewModule.module +
            InAppUpdateModule.module
}