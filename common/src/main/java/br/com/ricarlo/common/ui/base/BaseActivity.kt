package br.com.ricarlo.common.ui.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import br.com.ricarlo.common.R
import br.com.ricarlo.common.inapp.update.Event
import br.com.ricarlo.common.inapp.update.IN_APP_UPDATE_REQUEST_CODE
import br.com.ricarlo.common.inapp.update.TAG_UPDATE
import br.com.ricarlo.common.inapp.update.UpdateViewModel
import br.com.ricarlo.common.util.exception.SessionException
import br.com.ricarlo.common.util.extensions.showToast
import br.com.ricarlo.network.ApiException
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.ktx.AppUpdateResult
import com.google.android.play.core.ktx.bytesDownloaded
import com.google.android.play.core.ktx.totalBytesToDownload
import org.koin.android.ext.android.inject
import java.io.IOException

/**
 * Created by ricarlo on 10/11/2016.
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun initView(savedInstanceState: Bundle?)

    protected val binding: T by lazy {
        DataBindingUtil.setContentView<T>(this, getLayoutRes())
            .apply {
                lifecycleOwner = this@BaseActivity
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            R.string.update_available,
            Snackbar.LENGTH_INDEFINITE
        )
        subscribeUI()

        initView(savedInstanceState = savedInstanceState)
    }

    private val appUpdateManager: AppUpdateManager by inject()
    private val updateViewModel: UpdateViewModel by inject()

    private lateinit var snackbar: Snackbar

    private var progressDialog: MaterialDialog? = null
    private var runningBackground = false

    override fun onResume() {
        super.onResume()
        runningBackground = false
    }

    override fun onPause() {
        super.onPause()
        runningBackground = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUI() {
        with(updateViewModel) {
            updateStatus.observe(
                this@BaseActivity,
                Observer { updateResult ->
                    when (updateResult) {
                        is AppUpdateResult.NotAvailable -> {
                            Log.d(TAG_UPDATE, "No update available")
                            snackbar.dismiss()
                        }
                        is AppUpdateResult.Available -> {
                            // If it's an immediate update, launch it immediately and finish Activity
                            // to prevent the user from using the app until they update.
                            if (updateViewModel.shouldLaunchImmediateUpdate(updateResult.updateInfo)) {
                                if (appUpdateManager.startUpdateFlowForResult(
                                        updateResult.updateInfo,
                                        AppUpdateType.IMMEDIATE,
                                        this@BaseActivity,
                                        IN_APP_UPDATE_REQUEST_CODE
                                    )
                                ) {
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
                                    (
                                        updateResult.installState.bytesDownloaded * 100 /
                                            updateResult.installState.totalBytesToDownload
                                        ).toInt()
                                }
                                setText(
                                    context.getString(
                                        R.string.downloading_update, updateProgress
                                    )
                                )
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
            )
            events.observe(
                this@BaseActivity,
                Observer { event ->
                    when (event) {
                        is Event.ToastEvent -> showToast(event.message)
                        is Event.StartUpdateEvent -> {
                            appUpdateManager.startUpdateFlowForResult(
                                event.updateInfo,
                                event.appUpdateType,
                                this@BaseActivity,
                                IN_APP_UPDATE_REQUEST_CODE
                            )
                        }
                        else -> throw IllegalStateException("Event type not handled: $event")
                    }
                }
            )
        }
    }

    private fun onActivityResult(requestCode: Int, result: androidx.activity.result.ActivityResult) {
        when (requestCode) {
            IN_APP_UPDATE_REQUEST_CODE -> {
                when (result.resultCode) {
                    RESULT_OK -> {
                        Log.e(TAG_UPDATE, "Update flow RESULT_OK")
                    }
                    RESULT_CANCELED -> {
                        Log.e(TAG_UPDATE, "Update flow RESULT_CANCELED")
                    }
                    ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                        Log.e(TAG_UPDATE, "Update flow RESULT_IN_APP_UPDATE_FAILED")
                    }
                    else -> {
                        Log.e(TAG_UPDATE, "Update flow failed! Result code: ${result.resultCode}")
                    }
                }
            }
        }
    }

    fun openActivity(classOpen: Class<*>, bundle: Bundle? = null, requestCode: Int? = null) {
        val intent = Intent(this, classOpen).apply {
            bundle?.let {
                putExtras(it)
            }
        }
        if (requestCode == null) {
            startActivity(intent)
        } else {
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onActivityResult(requestCode, result)
            }.launch(intent)
        }
    }

    open fun showLoading(message: String?) {
        if (progressDialog == null) {
            progressDialog = MaterialDialog.Builder(this)
                .content(getString(R.string.waiting))
                .cancelable(false)
                .progress(true, 0)
                .build()
        }

        progressDialog?.setContent(
            if (message.isNullOrEmpty()) getString(R.string.waiting) else message
        )

        if (!runningBackground) {
            progressDialog?.show()
        }
    }

    open fun hideLoading() {
        if (progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }

    fun handlerError(throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                showToast(getString(R.string.sem_conexao))
            }
            is ApiException -> {
                val message = throwable.message.takeIf { it.isNullOrEmpty().not() }
                    ?: getString(R.string.error_generic)
                showToast(message)
            }
            is SessionException -> {
                // TODO logout
            }
            else -> {
                showToast(getString(R.string.error_generic))
            }
        }
    }

    protected fun setUpToolBar(title: String?) {
        supportActionBar?.title = title
    }

    protected fun share(title: String, url: String) {
        startActivity(
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
//                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name)) TODO review
                putExtra(Intent.EXTRA_TEXT, "$title$url".trimIndent())
            }
        )
    }
}
