package br.com.ricarlo.common.inapp.update

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.com.ricarlo.common.firebase.remoteconfig.Feature
import br.com.ricarlo.common.firebase.remoteconfig.IFirebaseRemoteConfigManager
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.ktx.AppUpdateResult
import com.google.android.play.core.ktx.BuildConfig
import com.google.android.play.core.ktx.clientVersionStalenessDays
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import com.google.android.play.core.ktx.requestUpdateFlow
import com.google.android.play.core.ktx.updatePriority
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UpdateViewModel constructor(
    manager: AppUpdateManager,
    private val remoteConfigManager: IFirebaseRemoteConfigManager
) : ViewModel() {

    companion object {
        private const val DAYS = 30
        // 0 until 5
        private const val PRIORITY = 4
    }

    val updateStatus = manager.requestUpdateFlow()
        .catch {
            _events.postValue(Event.ToastEvent("Update info not available"))
        }
        .asLiveData()

    private val _events = MutableLiveData<Event>()
    val events: LiveData<Event> = _events

    fun shouldLaunchImmediateUpdate(updateInfo: AppUpdateInfo): Boolean {
        with(updateInfo) {
            return isImmediateUpdateAllowed &&
                (
                    currentIsOldVersion() ||
                        clientVersionStalenessDays ?: 0 > DAYS ||
                        updatePriority > PRIORITY
                    )
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
                    Log.d(TAG_UPDATE, "Update priority: $updatePriority")
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
const val TAG_UPDATE = "IN_APP_UPDATE"
