package com.noticias_now.app

import android.app.Application
import android.text.TextUtils
import br.com.ricarlo.common.di.CommonModule
import com.noticias_now.model.UsuarioModel
import com.noticias_now.util.ApsNoticiasUtils
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by ricarlo on 10/11/2016.
 */
class ApsNoticiasApp : Application() {

    var usuario: UsuarioModel? = null
        private set

    override fun onCreate() {
        super.onCreate()
        startDI()
        instance = this
        verifyExistUser()
    }

    private fun verifyExistUser() {
        val jsonUser = ApsNoticiasUtils.read(instance, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DATA_USER, null)
        if (!TextUtils.isEmpty(jsonUser)) {
            usuario = ApsNoticiasUtils.jsonToObject(jsonUser, UsuarioModel::class.java) as UsuarioModel
        }
    }

    fun checkIfLogged(): Boolean {
        return usuario != null
    }

    fun saveUser(user: UsuarioModel?) {
        if (user != null) {
            ApsNoticiasUtils.write(instance, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DATA_USER, ApsNoticiasUtils.objectToString(user))
            usuario = user
        }
    }

    fun doLogout() {
        usuario = null
        saveUserInfo("")
        ApsNoticiasUtils.write(this, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DEVICE_TOKEN, "")
        ApsNoticiasUtils.remove(this, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DEVICE_TOKEN)
    }

    private fun startDI() {
        startKoin {
            // Android context
            androidContext(this@ApsNoticiasApp)
            // modules
            modules(CommonModule.modules)
        }
    }

    companion object {
        @JvmStatic
        var instance: ApsNoticiasApp? = null
            private set
        private fun saveUserInfo(userJSON: String) {
            ApsNoticiasUtils.write(instance, Constants.SharedPreferences.PATH_APP, Constants.SharedPreferences.DATA_USER, userJSON)
        }
    }
}