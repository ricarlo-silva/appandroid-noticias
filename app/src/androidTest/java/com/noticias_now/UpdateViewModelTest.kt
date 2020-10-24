package com.noticias_now

import androidx.appcompat.widget.AppCompatButton
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.noticias_now.ui.activity.SplashActivity
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UpdateViewModelTest {

    private lateinit var fakeAppUpdateManager : FakeAppUpdateManager

    @Before
    fun sutup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        fakeAppUpdateManager = FakeAppUpdateManager(appContext)
    }
    @Test
    fun testFlexibleUpdate_Completes() {
        // Setup flexible update.
//        fakeAppUpdateManager.partiallyAllowedUpdateType = AppUpdateType.FLEXIBLE
        fakeAppUpdateManager.setUpdateAvailable(2)

        ActivityScenario.launch(SplashActivity::class.java)

        // Validate that flexible update is prompted to the user.
        Assert.assertTrue(fakeAppUpdateManager.isConfirmationDialogVisible)

        // Simulate user's and download behavior.
        fakeAppUpdateManager.userAcceptsUpdate()

        fakeAppUpdateManager.downloadStarts()

        fakeAppUpdateManager.downloadCompletes()

        // Perform a click on the Snackbar to complete the update process.
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.isDescendantOfA(CoreMatchers.instanceOf(Snackbar.SnackbarLayout::class.java)),
                CoreMatchers.instanceOf(AppCompatButton::class.java)
            )
        ).perform(ViewActions.click())

        // Validate that update is completed and app is restarted.
        Assert.assertTrue(fakeAppUpdateManager.isInstallSplashScreenVisible)

        fakeAppUpdateManager.installCompletes()
    }
}