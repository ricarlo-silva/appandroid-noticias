package com.noticias_now.account.register

import br.com.ricarlo.common.util.exception.SessionException
import br.com.ricarlo.network.apiCall
import br.com.ricarlo.common.util.coroutines.ICoroutinesDispatcherProvider
import com.noticias_now.login.SessionQuery
import com.noticias_now.model.request.SessionRequest
import com.noticias_now.model.UserModel
import com.noticias_now.services.IWebService

class UserRepositoryImpl(
        private val remote: IWebService,
        private val local: IUserRepositoryLocal,
        private val dispatchers: ICoroutinesDispatcherProvider
) : IUserRepository {

    override suspend fun insert(user: UserModel) {
        return apiCall(dispatchers.io()) {
            local.save(remote.registerUser(user).data)
        }
    }

    override suspend fun get(): UserModel {
        return local.get() ?: throw SessionException()
    }

    override suspend fun update(user: UserModel) {
        return apiCall(dispatchers.io()) {
            local.save(remote.updateUser(user).data)
        }
    }

    override suspend fun login(session: SessionQuery.SingIn) {
        return apiCall(dispatchers.io()) {
            local.save(remote.login(SessionRequest(
                    email = session.email,
                    password = session.password
            )).data)
        }
    }

    override suspend fun logout() {
        local.delete()
    }

    override suspend fun checkIfLogged(): Boolean {
        return local.get() != null
    }
}