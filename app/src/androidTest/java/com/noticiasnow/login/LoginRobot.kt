package com.noticiasnow.login

import android.app.Activity
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasImeAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.noticiasnow.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.hamcrest.core.IsInstanceOf

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
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
    }

    fun clickEnter() {
        onView(withId(R.id.bt_login))
            .perform(ViewActions.click())
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
        onView(withId(R.id.bt_create_account))
            .perform(ViewActions.scrollTo(), ViewActions.click())
    }

    fun isRegisterScreen() {
        onView(withId(R.id.bt_register))
            .check(matches(isDisplayed()))
//        Intents.intended(IntentMatchers.toPackage("com.noticias_now.account.register"))
    }

    fun isHomeScreen() {
        val textView = onView(
            Matchers.allOf(
                withText("Noticias NOW"),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        withId(R.id.toolbar),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Noticias NOW")))

        val textView2 = onView(
            Matchers.allOf(
                withText("Poluição do ar"),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        ViewMatchers.withContentDescription("Poluição do ar"),
                        ViewMatchers.withParent(IsInstanceOf.instanceOf(LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Poluição do ar")))

//        val textView3 = onView(
//            Matchers.allOf(
//                withId(R.id.tv_nome_user),
//                withText("test"),
//                ViewMatchers.withParent(ViewMatchers.withParent(withId(R.id.card_view))),
//                isDisplayed()
//            )
//        )
//        textView3.check(matches(withText("test")))

//        val textView4 = onView(
//            Matchers.allOf(
//                withId(R.id.tv_title),
//                withText("test 1"),
//                ViewMatchers.withParent(ViewMatchers.withParent(withId(R.id.card_view))),
//                isDisplayed()
//            )
//        )
//        textView4.check(matches(withText("test 1")))

//        val textView5 = onView(
//            Matchers.allOf(
//                withId(R.id.tv_description),
//                withText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed " +
//                        "do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut " +
//                        "enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
//                        "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
//                        "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla " +
//                        "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in " +
//                        "culpa qui officia deserunt mollit anim id est laborum."),
//                ViewMatchers.withParent(ViewMatchers.withParent(withId(R.id.card_view))),
//                isDisplayed()
//            )
//        )
//        textView5.check(matches(withText("Lorem ipsum dolor sit amet, consectetur " +
//                "adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna " +
//                "aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
//                "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
//                "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
//                "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
//                "deserunt mollit anim id est laborum.")))

    }
}
