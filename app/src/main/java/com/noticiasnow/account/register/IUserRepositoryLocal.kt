package com.noticiasnow.account.register

import com.noticiasnow.model.UserModel

interface IUserRepositoryLocal {

    suspend fun save(user: UserModel)

    suspend fun delete()

    suspend fun get(): UserModel?
}
