package com.noticiasnow.account.register

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import br.com.ricarlo.network.fromJson
import br.com.ricarlo.network.toJson
import com.noticiasnow.model.UserModel
import com.ricarlo.storage.delete
import com.ricarlo.storage.get
import com.ricarlo.storage.save

class UserRepositoryLocalImpl(
    private val storage: DataStore<Preferences>
) : IUserRepositoryLocal {

    companion object {
        const val DATA_USER = "data_user"
    }

    override suspend fun save(user: UserModel) {
        user.toJson()?.let {
            storage.save<String>(DATA_USER, it)
        }
    }

    override suspend fun delete() {
        storage.delete<String>(DATA_USER)
    }

    override suspend fun get(): UserModel? {
        val jsonUser = storage.get<String>(DATA_USER)
        return fromJson<UserModel>(jsonUser)
    }
}
