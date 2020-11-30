package com.noticias_now.account.register

import android.content.Context
import br.com.ricarlo.network.fromJson
import br.com.ricarlo.network.toJson
import com.noticias_now.app.Constants
import com.noticias_now.model.UserModel
import br.com.ricarlo.common.util.ApsNoticiasUtils

// TODO refactor all
class UserRepositoryLocalImpl(
        private val context: Context
) : IUserRepositoryLocal {

    override fun save(user: UserModel) {
        ApsNoticiasUtils.write(context, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DATA_USER, user.toJson())
    }

    override fun delete() {
        ApsNoticiasUtils.remove(context, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DATA_USER)
    }

    override fun get(): UserModel? {
        val jsonUser = ApsNoticiasUtils.read(context, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DATA_USER, null)
        return fromJson<UserModel>(jsonUser)
    }
}