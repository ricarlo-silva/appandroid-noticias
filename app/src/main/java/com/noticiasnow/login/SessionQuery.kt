package com.noticiasnow.login

import com.noticiasnow.model.BaseQuery

sealed class SessionQuery : BaseQuery {
    class SingIn(val email: String, val password: String) : SessionQuery()
//    object SingOut : SessionQuery()
}
