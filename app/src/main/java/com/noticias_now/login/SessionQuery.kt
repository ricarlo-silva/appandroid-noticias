package com.noticias_now.login

import com.noticias_now.model.BaseQuery

sealed class SessionQuery : BaseQuery {
    class SingIn(val email: String, val password: String) : SessionQuery()
    object SingOut : SessionQuery()
}