package br.com.ricarlo.common.inapp.update

import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.model.AppUpdateType

sealed class Event {
    data class ToastEvent(val message: String) : Event()
    data class StartUpdateEvent(
            val updateInfo: AppUpdateInfo,
            @AppUpdateType val appUpdateType: Int
    ) : Event()
}
