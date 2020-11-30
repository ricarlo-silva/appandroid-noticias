package com.noticias_now.splash

sealed class SplashEvent {
    object LaunchHome : SplashEvent()
    object LaunchLogin : SplashEvent()
}