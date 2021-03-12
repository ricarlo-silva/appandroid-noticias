package com.noticiasnow.login

import android.app.Activity
import android.view.inputmethod.EditorInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasImeAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.noticiasnow.R
import org.hamcrest.Matchers.not

fun login(func: LoginRobot.() -> Unit) = LoginRobot().apply { func() }

class LoginRobot {

    fun typeEmail(email: String) {
        onView(withId(R.id.ed_email))
            .check(matches(withHint("E-mail")))
            .check(matches(hasImeAction(EditorInfo.IME_ACTION_NEXT)))
            .perform(ViewActions.typeText(email), ViewActions.pressImeActionButton())
    }

    fun typePassword(password: String) {
        onView(withId(R.id.ed_password))
            .check(matches(withHint("Senha")))
            .check(matches(hasImeAction(EditorInfo.IME_ACTION_DONE)))
            .perform(ViewActions.typeText(password), ViewActions.pressImeActionButton())
    }

    fun clickEnter() {
        onView(withId(R.id.bt_login)).perform(ViewActions.click())
    }

    fun isDisplayedToast(message: String) {
        onView(withText(message)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

//        onView(withText(""))
//            .inRoot(RootMatchers.withDecorView(not(activityRule.getDecorView())))
//            .check(matches(isDisplayed()))
    }

    fun Activity.isDisplayedToast(message: String) {
        onView(withText(message))
            .inRoot(RootMatchers.withDecorView(not(window.decorView)))
            .check(matches(isDisplayed()))
    }

    fun clickRegister() {
        onView(withId(R.id.bt_create_account)).perform(ViewActions.click())
    }

    fun isRegisterScreen() {
        onView(withId(R.id.bt_register)).check(matches(isDisplayed()))
//        Intents.intended(IntentMatchers.toPackage("com.noticias_now.account.register"))
    }

    fun isHomeScreen() {
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
    }
}
