package com.noticias_now.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import br.com.ricarlo.common.inapp.update.Event
import br.com.ricarlo.common.inapp.update.IN_APP_UPDATE_REQUEST_CODE
import br.com.ricarlo.common.inapp.update.UpdateViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.ktx.AppUpdateResult
import com.google.android.play.core.ktx.bytesDownloaded
import com.google.android.play.core.ktx.totalBytesToDownload
import com.noticias_now.R
import com.noticias_now.util.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity() {

    companion object {
        private const val TIME_DELAY: Long = 3000
    }

    private val appUpdateManager: AppUpdateManager by inject()
    private val updateViewModel: UpdateViewModel by inject()


    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                R.string.update_available,
                Snackbar.LENGTH_INDEFINITE
        )

        subscribeUI()

    }

    private fun subscribeUI() {
        with(updateViewModel) {
            updateStatus.observe(this@SplashActivity) { updateResult ->
                when (updateResult) {
                    is AppUpdateResult.NotAvailable -> {
                        Log.d(br.com.ricarlo.common.inapp.update.TAG, "No update available")
                        snackbar.dismiss()
                        goToLogin()
                    }
                    is AppUpdateResult.Available -> {
                        // If it's an immediate update, launch it immediately and finish Activity
                        // to prevent the user from using the app until they update.
                        if (updateViewModel.shouldLaunchImmediateUpdate(updateResult.updateInfo)) {
                            if (appUpdateManager.startUpdateFlowForResult(
                                            updateResult.updateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            this@SplashActivity,
                                            IN_APP_UPDATE_REQUEST_CODE
                                    )) {
                                // only exit if update flow really started
                                finish()
                            }
                        } else {
                            with(snackbar) {
                                setText(R.string.update_available_snackbar)
                                setAction(R.string.update_now) {
                                    updateViewModel.invokeUpdate()
                                }
                                show()
                            }
                        }
                    }
                    is AppUpdateResult.InProgress -> {
                        with(snackbar) {
                            val updateProgress = if (updateResult.installState.totalBytesToDownload == 0L) {
                                0
                            } else {
                                (updateResult.installState.bytesDownloaded * 100 /
                                        updateResult.installState.totalBytesToDownload).toInt()
                            }
                            setText(context.getString(R.string.downloading_update, updateProgress))
                            setAction(null) {}
                            show()
                        }
                    }
                    is AppUpdateResult.Downloaded -> {
                        with(snackbar) {
                            setText(R.string.update_downloaded)
                            setAction(R.string.complete_update) {
                                updateViewModel.invokeUpdate()
                            }
                            show()
                        }
                    }
                }

            }
            events.observe(this@SplashActivity) { event ->
                when (event) {
                    is Event.ToastEvent -> showToast(event.message)
                    is Event.StartUpdateEvent -> {
                        appUpdateManager.startUpdateFlowForResult(
                                event.updateInfo,
                                event.appUpdateType,
                                this@SplashActivity,
                                IN_APP_UPDATE_REQUEST_CODE
                        )
                    }
                    else -> throw IllegalStateException("Event type not handled: $event")
                }
            }
        }
    }

    private fun goToLogin() {
        lifecycleScope.launch {
            delay(TIME_DELAY)
            openActivity(this@SplashActivity, LoginActivity::class.java)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            IN_APP_UPDATE_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        Log.e(br.com.ricarlo.common.inapp.update.TAG, "Update flow RESULT_OK")
                    }
                    RESULT_CANCELED -> {
                        Log.e(br.com.ricarlo.common.inapp.update.TAG, "Update flow RESULT_CANCELED")
                        goToLogin()
                    }
                    ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                        Log.e(br.com.ricarlo.common.inapp.update.TAG, "Update flow RESULT_IN_APP_UPDATE_FAILED")
                    }
                    else -> {
                        Log.e(br.com.ricarlo.common.inapp.update.TAG, "Update flow failed! Result code: $resultCode")
                    }
                }
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

}