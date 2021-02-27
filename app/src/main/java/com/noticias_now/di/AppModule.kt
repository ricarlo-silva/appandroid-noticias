package com.noticias_now.di

import br.com.ricarlo.network.INetworkConfig
import br.com.ricarlo.network.Service
import com.noticias_now.BuildConfig
import com.noticias_now.account.register.*
import com.noticias_now.account.update.EditProfileViewModel
import com.noticias_now.app.NetworkConfigImpl
import com.noticias_now.details.DetailsViewModel
import com.noticias_now.details.INewsRepository
import com.noticias_now.details.NewsRepositoryImpl
import com.noticias_now.home.AccountViewModel
import com.noticias_now.home.HomeViewModel
import com.noticias_now.home.NewsTypeViewModel
import com.noticias_now.list.UserNewsViewModel
import com.noticias_now.login.LoginViewModel
import com.noticias_now.publish.PublishViewModel
import com.noticias_now.services.IWebService
import com.noticias_now.services.MockWebService
import com.noticias_now.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
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
