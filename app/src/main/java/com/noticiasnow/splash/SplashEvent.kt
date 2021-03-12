package com.noticiasnow.splash

sealed class SplashEvent {
    object LaunchHome : SplashEvent()
    object LaunchLogin : SplashEvent()
}
