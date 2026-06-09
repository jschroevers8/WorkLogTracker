package worklogtracker.plugins.koin

import org.koin.core.module.dsl.*
import org.koin.dsl.module
import worklogtracker.data.local.AuthManager
import worklogtracker.data.local.AuthManagerInterface
import worklogtracker.data.remote.ApiClient
import worklogtracker.data.remote.KtorApi
import worklogtracker.presentation.admin.renterrequests.AdminRenterRequestsViewModel
import worklogtracker.presentation.advertisement.create.CreateAdvertisementViewModel
import worklogtracker.presentation.advertisement.overview.show.ShowAdvertisementViewModel
import worklogtracker.presentation.advertisement.overview.AvailableAdvertisementsViewModel
import worklogtracker.presentation.car.create.CreateCarViewModel
import worklogtracker.presentation.framework.camera.CameraService
import worklogtracker.presentation.advertisement.overview.homepage.HomepageViewModel
import worklogtracker.presentation.rental.create.CreateRentalViewModel
import worklogtracker.presentation.car.personal.PersonalCarsViewModel
import worklogtracker.presentation.car.personal.edit.EditPersonalCarsViewModel
import worklogtracker.presentation.car.recommended.RecommendedCarViewModel
import worklogtracker.presentation.rental.myrentals.MyRentalsViewModel
import worklogtracker.presentation.rentaltrip.register.RegisterTripViewModel
import worklogtracker.presentation.user.account.AccountViewModel
import worklogtracker.presentation.user.address.AddressViewModel
import worklogtracker.presentation.user.login.LoginViewModel
import worklogtracker.presentation.user.signup.SignupViewModel
import worklogtracker.repositories.AdvertisementRepository
import worklogtracker.repositories.CarRepository
import worklogtracker.repositories.RentalRepository
import worklogtracker.repositories.UserRepository

val appModule = module {
    single { UserRepository(get()) }
    single { AdvertisementRepository(get()) }
    single { CarRepository(get()) }
    single { RentalRepository(get()) }
    single<ApiClient> { KtorApi(get()) }
    single<AuthManagerInterface> { AuthManager(get ()) }
    single { CameraService(get()) }

    viewModel { CreateAdvertisementViewModel(get(), get(), get()) }
    viewModel { AvailableAdvertisementsViewModel(get()) }
    viewModel { CreateCarViewModel(get()) }
    viewModel { PersonalCarsViewModel(get()) }
    viewModel { AccountViewModel(get(), get()) }
    viewModel { HomepageViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { AddressViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ShowAdvertisementViewModel(get(), get()) }
    viewModel { EditPersonalCarsViewModel(get()) }
    viewModel { AdminRenterRequestsViewModel(get()) }
    viewModel { CreateRentalViewModel(get()) }
    viewModel { MyRentalsViewModel(get()) }
    viewModel { RegisterTripViewModel(get()) }
    viewModel { RecommendedCarViewModel(get()) }
}
