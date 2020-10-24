package br.com.ricarlo.common.inapp.update

import android.util.Log
import androidx.lifecycle.*
import br.com.ricarlo.common.firebase.remoteconfig.Feature
import br.com.ricarlo.common.firebase.remoteconfig.IFirebaseRemoteConfigManager
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.ktx.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UpdateViewModel constructor(
        manager: AppUpdateManager,
        private val remoteConfigManager: IFirebaseRemoteConfigManager
) : ViewModel() {

    val updateStatus = manager.requestUpdateFlow()
            .catch {
                _events.postValue(Event.ToastEvent("Update info not available"))
            }
            .asLiveData()

    private val _events = MutableLiveData<Event>()
    val events: LiveData<Event> = _events

    fun shouldLaunchImmediateUpdate(updateInfo: AppUpdateInfo): Boolean {
        with(updateInfo) {
            return isImmediateUpdateAllowed
                    &&
                    (currentIsOldVersion()
                            || clientVersionStalenessDays ?: 0 > 30
                            || updatePriority > 4)
        }
    }

    private fun currentIsOldVersion(): Boolean {
        return remoteConfigManager
                .fetchSync(Feature.MIN_VERSION, clazz = Long::class.java) > BuildConfig.VERSION_CODE
    }

    fun invokeUpdate() {
        when (val updateResult = updateStatus.value) {
            is AppUpdateResult.NotAvailable -> {
                _events.postValue(Event.ToastEvent("No update available"))
            }
            is AppUpdateResult.Available -> {
                with(updateResult.updateInfo) {
                    Log.d(TAG, "Update priority: $updatePriority")
                    when {
                        shouldLaunchImmediateUpdate(this) -> {
                            _events.postValue(
                                    Event.StartUpdateEvent(
                                            updateResult.updateInfo, AppUpdateType.IMMEDIATE
                                    )
                            )
                        }
                        isFlexibleUpdateAllowed -> {
                            _events.postValue(
                                    Event.StartUpdateEvent(
                                            updateResult.updateInfo, AppUpdateType.FLEXIBLE
                                    )
                            )
                        }
                        else -> {
                            throw IllegalStateException("Not implemented: Handling for $this")
                        }
                    }
                }
            }
            is AppUpdateResult.InProgress -> {
                _events.postValue(Event.ToastEvent("Update already in progress"))
            }
            is AppUpdateResult.Downloaded -> {
                viewModelScope.launch {
                    runCatching { updateResult.completeUpdate() }
                            .onFailure {
                                _events.postValue(Event.ToastEvent("No complete update"))
                            }
                }
            }
        }
    }
}

const val IN_APP_UPDATE_REQUEST_CODE = 123
const val TAG = "IN_APP_UPDATE"
