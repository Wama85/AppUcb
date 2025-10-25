package com.calyrsoft.ucbp1.di

import NotificationViewModel
import com.calyrsoft.ucbp1.core.AuthManager
import com.calyrsoft.ucbp1.features.dollar.data.database.AppRoomDatabase
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.data.repository.DollarRepositoryImpl
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarHistoryViewModel
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarViewModel
import com.calyrsoft.ucbp1.features.github.data.api.GithubService
import com.calyrsoft.ucbp1.features.github.data.datasource.GithubRemoteDataSource
import com.calyrsoft.ucbp1.features.github.data.repository.GithubRepository
import com.calyrsoft.ucbp1.features.github.domain.repository.IGithubRepository
import com.calyrsoft.ucbp1.features.github.domain.usecase.FindByNickNameUseCase
import com.calyrsoft.ucbp1.features.github.presentation.GithubViewModel
import com.calyrsoft.ucbp1.features.login.data.LoginRepositoryImpl
import com.calyrsoft.ucbp1.features.login.data.datasource.LoginDataStore
import com.calyrsoft.ucbp1.features.login.domain.repository.LoginRepository
import com.calyrsoft.ucbp1.features.login.domain.usecase.CheckLoginStatusUseCase
import com.calyrsoft.ucbp1.features.login.domain.usecase.GetUserSessionUseCase
import com.calyrsoft.ucbp1.features.login.domain.usecase.LoginUseCase
import com.calyrsoft.ucbp1.features.login.domain.usecase.LogoutUseCase
import com.calyrsoft.ucbp1.features.login.presentation.LoginViewModel
import com.calyrsoft.ucbp1.features.movie.data.api.MovieService
import com.calyrsoft.ucbp1.features.movie.data.database.MovieRoomDatabase
import com.calyrsoft.ucbp1.features.movie.data.datasource.remote.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.data.repository.MovieRepositoryImpl
import com.calyrsoft.ucbp1.features.movie.data.repository.MovieLocalRepository
import com.calyrsoft.ucbp1.features.movie.domain.repository.MovieRepository
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMovieRepository
import com.calyrsoft.ucbp1.features.movie.domain.usercase.GetMovieByIdUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.GetPopularMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.GetSavedMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.GetWatchLaterMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.SaveMovieUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.ToggleWatchLaterUseCase
import com.calyrsoft.ucbp1.features.movie.domain.usercase.UpdateMovieRatingUseCase
import com.calyrsoft.ucbp1.features.movie.presentation.viewmodel.MovieDetailViewModel
import com.calyrsoft.ucbp1.features.movie.presentation.viewmodel.MoviesViewModel
import com.calyrsoft.ucbp1.features.notification.data.repository.NotificationRepository
import com.calyrsoft.ucbp1.features.notification.data.repository.NotificationRepositoryImpl
import com.calyrsoft.ucbp1.features.profile.data.ProfileRepositoryImpl
import com.calyrsoft.ucbp1.features.profile.domain.repository.ProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.usecase.GetProfileUseCase
import com.calyrsoft.ucbp1.features.profile.presentation.ProfileViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    // AuthManager (puedes mantenerlo o migrarlo completamente a DataStore)
    single { AuthManager(get()) }

    // LoginDataStore - NUEVO
    single { LoginDataStore(get()) }

    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Services
    single<GithubService> {
        get<Retrofit>().create(GithubService::class.java)
    }

    single<MovieService> {
        get<Retrofit>().newBuilder()
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
            .create(MovieService::class.java)
    }

    // Repositories
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single { com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource() }
    single<ProfileRepository> { ProfileRepositoryImpl() }
    single<IDollarRepository> { DollarRepositoryImpl(get(), get()) }
    single { GithubRemoteDataSource(get()) }
    single<IGithubRepository> { GithubRepository(get()) }
    single { MovieRemoteDataSource(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single { NotificationRepositoryImpl() as NotificationRepository }
    
    // Dollar Database
    single { AppRoomDatabase.getDatabase(get()) }
    single { get<AppRoomDatabase>().dollarDao() }
    single { DollarLocalDataSource(get()) }
    
    // Movie Database - NUEVO
    single { MovieRoomDatabase.getDatabase(get()) }
    single { get<MovieRoomDatabase>().movieDao() }
    single<IMovieRepository> { MovieLocalRepository(get()) }

    // UseCases
    factory { LoginUseCase(get()) }
    factory { GetUserSessionUseCase(get()) }
    factory { CheckLoginStatusUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { FetchDollarUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { FindByNickNameUseCase(get()) }
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetMovieByIdUseCase(get()) }
    factory { SaveMovieUseCase(get()) }
    factory { UpdateMovieRatingUseCase(get()) }
    factory { GetSavedMoviesUseCase(get()) }
    factory { ToggleWatchLaterUseCase(get()) }
    factory { GetWatchLaterMoviesUseCase(get()) }

    // ViewModels
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(),get(),get()) }
    viewModel { DollarViewModel(get(), get()) }
    viewModel { GithubViewModel(get(), get()) }
    viewModel { MoviesViewModel(get(), get(), get(), get(), get()) }
    viewModel { DollarHistoryViewModel(get()) }
    viewModel { NotificationViewModel(get()) }
    viewModel { MovieDetailViewModel(get(), get()) }
}
