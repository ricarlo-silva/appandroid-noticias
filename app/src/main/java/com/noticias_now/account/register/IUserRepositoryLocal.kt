package com.noticias_now.account.register

import com.noticias_now.model.UserModel

interface IUserRepositoryLocal {

    suspend fun save(user: UserModel)

    suspend fun delete()

    suspend fun get(): UserModel?
}
