package com.noticiasnow.di

import br.com.ricarlo.network.INetworkConfig
import br.com.ricarlo.network.Service
import com.noticiasnow.BuildConfig
import com.noticiasnow.account.register.IUserRepository
import com.noticiasnow.account.register.IUserRepositoryLocal
import com.noticiasnow.account.register.RegisterViewModel
import com.noticiasnow.account.register.UserRepositoryImpl
import com.noticiasnow.account.register.UserRepositoryLocalImpl
import com.noticiasnow.account.update.EditProfileViewModel
import com.noticiasnow.app.NetworkConfigImpl
import com.noticiasnow.details.DetailsViewModel
import com.noticiasnow.details.INewsRepository
import com.noticiasnow.details.NewsRepositoryImpl
import com.noticiasnow.home.AccountViewModel
import com.noticiasnow.home.HomeViewModel
import com.noticiasnow.home.NewsTypeViewModel
import com.noticiasnow.list.UserNewsViewModel
import com.noticiasnow.login.LoginViewModel
import com.noticiasnow.publish.PublishViewModel
import com.noticiasnow.services.IWebService
import com.noticiasnow.services.MockWebService
import com.noticiasnow.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module

@KoinApiExtension
object AppModule {
    val modules = listOf(
        module {
            single<INetworkConfig> {
                NetworkConfigImpl()
            }
            single<IWebService> {
                if (BuildConfig.DEBUG) MockWebService()
                else Service(get()).createService(IWebService::class.java)
//                    else ServiceGenerator.createService(IWebService::class.java, IWebService.URL_BASE)
            }

            single<IUserRepository> { UserRepositoryImpl(get(), get(), get()) }
            single<IUserRepositoryLocal> { UserRepositoryLocalImpl(get()) }
            single<INewsRepository> { NewsRepositoryImpl(get(), get()) }

            viewModel { RegisterViewModel(get(), get(), get()) }
            viewModel { EditProfileViewModel(get(), get(), get()) }
            viewModel { LoginViewModel(get(), get(), get(), get()) }
            viewModel { DetailsViewModel(get(), get(), get()) }
            viewModel { PublishViewModel(get(), get(), get(), get()) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { AccountViewModel(get(), get()) }
            viewModel { NewsTypeViewModel(get(), get()) }
            viewModel { UserNewsViewModel(get(), get(), get()) }
            viewModel { SplashViewModel(get(), get()) }
        }
    )
}
