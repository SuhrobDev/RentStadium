package dev.soul.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.soul.auth.login.LoginViewModel
import dev.soul.auth.login.ui.LoginRoot
import dev.soul.auth.otp.VerifyIntent
import dev.soul.auth.otp.VerifyViewModel
import dev.soul.auth.otp.ui.OtpRoot
import dev.soul.auth.register_info.RegisterEvent
import dev.soul.auth.register_info.RegisterViewModel
import dev.soul.auth.register_info.ui.RegisterInfoRoot
import dev.soul.auth.register_phone.RegisterPhoneViewModel
import dev.soul.auth.register_phone.ui.RegisterPhoneRoot
import dev.soul.base.ui.BaseGraphRoot
import dev.soul.more.MoreEvent
import dev.soul.more.MoreType
import dev.soul.more.MoreViewModel
import dev.soul.more.ui.MoreRoot
import dev.soul.search.SearchViewModel
import dev.soul.search.ui.map.MapSearchViewModel
import dev.soul.search.ui.map.ui.MapSearchRoot
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.TextView
import dev.soul.shared.navigation.Screen
import dev.soul.stadium_detail.StadiumDetailEvent
import dev.soul.stadium_detail.StadiumDetailViewModel
import dev.soul.stadium_detail.ui.StadiumDetailRoot
import dev.soul.user.home.HomeViewModel
import dev.soul.validation.ValidationViewModel
import dev.soul.validation.ui.ValidationRoot
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Validation) {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            composable<Screen.Validation> {
                val viewModel: ValidationViewModel = koinViewModel<ValidationViewModel>()
                ValidationRoot(viewModel = viewModel, onNavigate = {
                    navController.navigate(it) {
                        popUpTo<Screen.Validation> { inclusive = true }
                    }
                })
            }

            composable<Screen.Base> {
                val homeViewModel: HomeViewModel = koinViewModel<HomeViewModel>()
                val searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>()

                BaseGraphRoot(
                    homeViewModel = homeViewModel,
                    searchViewModel = searchViewModel,
                    onNotification = {
                        navController.navigate(Screen.Notification)
                    },
                    onSearchOption = {
                        navController.navigate(it)
                    },
                    onDetail = {
                        navController.navigate(Screen.StadiumDetail(it))
                    },
                    onMore = {
                        if (it)
                            navController.navigate(Screen.More(isPopular = true))
                        else
                            navController.navigate(Screen.More(isPersonalized = true))
                    }
                )
            }

            composable<Screen.MapSearch> {
                val viewModel: MapSearchViewModel = koinViewModel<MapSearchViewModel>()
                MapSearchRoot(
                    viewModel = viewModel,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    onStadiumDetail = {
                        navController.navigate(it)
                    },
                    onNavigateUp = {
                        navController.navigateUp()
                    }
                )
            }

            composable<Screen.ByStadium> {
                BaseBox {
                    TextView(text = "soon")
                }
            }
            composable<Screen.BySaved> {
                BaseBox {
                    TextView(text = "soon")
                }
            }

            composable<Screen.Notification> {
                BaseBox {
                    TextView(text = "soon")
                }
            }

            composable<Screen.Login> {
                val viewModel: LoginViewModel = koinViewModel<LoginViewModel>()

                LoginRoot(
                    viewModel = viewModel,
                    onNavigateHome = {
                        navController.navigate(Screen.Base) {
                            popUpTo<Screen.Login> { inclusive = true }
                        }
                    },
                    onNavigateRegister = {
                        navController.navigate(Screen.RegPhone)
                    }
                )
            }

            composable<Screen.RegPhone> {
                val viewModel: RegisterPhoneViewModel = koinViewModel<RegisterPhoneViewModel>()

                RegisterPhoneRoot(
                    viewModel = viewModel,
                    onBack = {
                        navController.navigateUp()
                    },
                    onNavigateOtp = {
                        navController.navigate(Screen.VerifyCode(it))
                    }
                )
            }

            composable<Screen.VerifyCode> {
                val viewModel: VerifyViewModel = koinViewModel<VerifyViewModel>()
                val params = it.toRoute<Screen.VerifyCode>()

                LaunchedEffect(Unit) {
                    viewModel.onAction(VerifyIntent.OnGetOtp(params.phone))
                }

                OtpRoot(
                    viewModel = viewModel,
                    onBack = {
                        navController.navigateUp()
                    },
                    onNavigateRegister = {
                        navController.navigate(Screen.Register(it))
                    }
                )
            }

            composable<Screen.Register> {
                val viewModel: RegisterViewModel = koinViewModel<RegisterViewModel>()

                val param = it.toRoute<Screen.Register>()

                LaunchedEffect(Unit) {
                    viewModel.onEvent(RegisterEvent.PhoneChanged(param.phone))
                }

                RegisterInfoRoot(
                    viewModel = viewModel,
                    onBack = {
                        navController.navigateUp()
                    },
                    onNavigateHome = {
                        navController.navigate(Screen.Base)
                    }
                )
            }

            composable<Screen.StadiumDetail> {
                val viewModel: StadiumDetailViewModel = koinViewModel<StadiumDetailViewModel>()

                val param = it.toRoute<Screen.StadiumDetail>()

                LaunchedEffect(Unit) {
                    viewModel.onEvent(StadiumDetailEvent.Detail(param.id))
                }

                StadiumDetailRoot(
                    viewModel = viewModel,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    onBack = {
                        navController.navigateUp()
                    }
                )
            }

            composable<Screen.More> {
                val param = it.toRoute<Screen.More>()
                val viewModel: MoreViewModel = koinViewModel<MoreViewModel>()

                LaunchedEffect(Unit) {
                    viewModel.onEvent(MoreEvent.Type(if (param.isPopular == true) MoreType.POPULAR else MoreType.PERSONALIZED))
                }

                MoreRoot(
                    viewModel = viewModel,
                    onDetail = {
                        navController.navigate(Screen.StadiumDetail(it))
                    },
                    onBack = {
                        navController.navigateUp()
                    }
                )

            }
        }
    }
}