package com.noticiasnow.login

import android.app.Activity
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

//    @get:Rule
//    var idlingResourceRule = EspressoIdlingResourceRule()

    @get:Rule
    var activityRule = activityScenarioRule<LoginActivity>()

    private lateinit var activity: Activity

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            activity = it
        }
    }

    @Test
    fun move_to_register_screen() {
        login {
            clickRegister()
            isRegisterScreen()
        }
    }

    @Test
    fun enter_with_empty_email_password() {
        login {
            typeEmail("")
            typePassword("")
            clickEnter()

//            activity.isDisplayedToast("Ocorreu um problema na conexão.\\nTente mais tarde.")
        }
    }

    @Test
    fun enter_with_valid_email_password() {
        login {
            typeEmail("test@email.com")
            typePassword("123456")
            clickEnter()
//            Intents.intended(IntentMatchers.hasComponent(ComponentName(ApplicationProvider.getApplicationContext(), HomeActivity::class.java.name)))

            isHomeScreen()
//            isDisplayedToast("Bem-vindo ao Notícias NOW")
        }
    }
}
