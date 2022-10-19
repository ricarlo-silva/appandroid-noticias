package com.noticiasnow.account.register

import com.noticiasnow.login.SessionQuery
import com.noticiasnow.model.UserModel

interface IUserRepository {
    suspend fun insert(user: UserModel)

    suspend fun get(): UserModel

    suspend fun update(user: UserModel)

    suspend fun login(session: SessionQuery.SingIn)

    suspend fun logout()

    suspend fun checkIfLogged(): Boolean
}
