package com.calyrsoft.ucbp1.di

import com.calyrsoft.ucbp1.features.dollar.data.DollarRepositoryImpl
import com.calyrsoft.ucbp1.features.dollar.domain.repository.DollarRepository
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.GetDollarUseCase

import com.calyrsoft.ucbp1.features.dollar.presentation.DollarViewModel
import com.calyrsoft.ucbp1.features.login.data.LoginRepositoryImpl
import com.calyrsoft.ucbp1.features.login.domain.repository.LoginRepository
import com.calyrsoft.ucbp1.features.login.domain.usecase.LoginUseCase
import com.calyrsoft.ucbp1.features.login.presentation.LoginViewModel
import com.calyrsoft.ucbp1.features.profile.data.ProfileRepositoryImpl
import com.calyrsoft.ucbp1.features.profile.domain.repository.ProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.usecase.GetProfileUseCase
import com.calyrsoft.ucbp1.features.profile.presentation.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repositories
    single<LoginRepository> { LoginRepositoryImpl() }
    single<DollarRepository> { DollarRepositoryImpl() }
    single<ProfileRepository> { ProfileRepositoryImpl() }
    // UseCases
    factory { LoginUseCase(get()) }
    factory { GetDollarUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    // ViewModels
    viewModel { LoginViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel{ DollarViewModel(get()) }
}