package com.noticias_now.account.register

import com.noticias_now.model.UserModel

interface IUserRepositoryLocal {

    fun save(user: UserModel)

    fun delete()

    fun get(): UserModel?
}