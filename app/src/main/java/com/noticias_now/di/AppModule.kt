package com.noticias_now.di

import br.com.ricarlo.network.ServiceGenerator
import com.noticias_now.BuildConfig
import com.noticias_now.account.register.*
import com.noticias_now.account.update.EditProfileViewModel
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
import org.koin.dsl.module


object AppModule {
    val modules = listOf(
            module {
                single<IWebService> {
                    if(BuildConfig.DEBUG) MockWebService()
                    else ServiceGenerator.createService(IWebService::class.java, IWebService.URL_BASE)
                }

                single<IUserRepository> { UserRepositoryImpl(get(), get(), get()) }
                single<IUserRepositoryLocal> { UserRepositoryLocalImpl(get()) }
                single<INewsRepository> { NewsRepositoryImpl(get(), get()) }

                viewModel<RegisterViewModel> { RegisterViewModel(get(), get(), get()) }
                viewModel<EditProfileViewModel> { EditProfileViewModel(get(), get(), get()) }
                viewModel<LoginViewModel> { LoginViewModel(get(), get(), get()) }
                viewModel<DetailsViewModel> { DetailsViewModel(get(), get(), get()) }
                viewModel<PublishViewModel> { PublishViewModel(get(), get(), get(), get()) }
                viewModel<HomeViewModel> { HomeViewModel(get(), get()) }
                viewModel { AccountViewModel(get(), get()) }
                viewModel<NewsTypeViewModel> { NewsTypeViewModel(get(), get()) }
                viewModel<UserNewsViewModel> { UserNewsViewModel(get(), get(), get()) }
                viewModel<SplashViewModel> { SplashViewModel(get(), get()) }

            }
    )
}