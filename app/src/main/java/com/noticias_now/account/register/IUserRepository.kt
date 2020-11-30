package com.noticias_now.account.register

import com.noticias_now.login.SessionQuery
import com.noticias_now.model.UserModel

interface IUserRepository {
    suspend fun insert(user: UserModel)

    suspend fun get(): UserModel

    suspend fun update(user: UserModel)

    suspend fun login(session: SessionQuery.SingIn)

    suspend fun logout()

    suspend fun checkIfLogged(): Boolean
}