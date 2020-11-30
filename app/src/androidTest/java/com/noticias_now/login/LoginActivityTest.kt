package com.noticias_now.login

import android.view.inputmethod.EditorInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.ricarlo.test.CoroutineTestRule
import com.noticias_now.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    var activityRule = activityScenarioRule<LoginActivity>()

//    // Executes tasks in the Architecture Components in the same thread
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun test1() = coroutinesTestRule.testDispatcher.runBlockingTest {


    }

    @Test
    fun test() {
//        onView(withId(R.id.bt_create_account)).perform(click())
//        Intents.intended(IntentMatchers.toPackage("com.noticias_now.account.register"))

        onView(withId(R.id.ed_email))
                .check(matches(withHint("E-mail")))
                .check(matches(hasImeAction(EditorInfo.IME_ACTION_NEXT)))
                .perform(typeText("test@email.com"), pressImeActionButton())
//                .perform(ViewActions.pressKey(EditorInfo.IME_ACTION_NEXT))

        onView(withId(R.id.ed_password))
                .check(matches(withHint("Senha")))
                .check(matches(hasImeAction(EditorInfo.IME_ACTION_DONE)))
                .perform(typeText("123456"), pressImeActionButton())


        onView(withId(R.id.bt_login)).perform(click())

        onView(withText("Bem-vindo ao Notícias NOW")).inRoot(ToastMatcher())
                .check(matches(isDisplayed()))

//        onView(withText(""))
//                .inRoot(RootMatchers.withDecorView(not(activityRule.getActivity().getWi

//                onView(withText(""))
//                        .inRoot(RootMatchers.withDecorView(not(activityRule..getDecorView())))
//                        .check(matches(isDisplayed()))


        // check toast visibility
        // check toast visibility
//        onView(withText("Pavneet Toast")).inRoot(withDecorView(not(`is`(activity.getWindow().getDecorView())))).check(matches(isDisplayed()))

//        onView(withId(R.id.bt_register)).check(matches(isDisplayed()))

    }

}