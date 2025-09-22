package dev.soul.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.TransformOrigin
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
import dev.soul.search.ui.map.MapSearchViewModel
import dev.soul.search.ui.map.ui.MapSearchRoot
import dev.soul.shared.components.BaseBox
import dev.soul.shared.components.TextView
import dev.soul.shared.navigation.Screen
import dev.soul.validation.ValidationViewModel
import dev.soul.validation.ui.ValidationRoot
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Validation) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        popEnterTransition = { EnterTransition.None },
        popExitTransition = {
            scaleOut(
                targetScale = 0.9f,
                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f)
            )
        }
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
            BaseGraphRoot(
                onNotification = {
                    navController.navigate(Screen.Notification)
                },
                onSearchOption = {
                    navController.navigate(it)
                }
            )
        }

        composable<Screen.MapSearch> {
            val viewModel: MapSearchViewModel = koinViewModel<MapSearchViewModel>()
            MapSearchRoot(
                viewModel = viewModel,
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
    }
}