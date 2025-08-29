package dev.soul.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.TransformOrigin
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.soul.auth.login.LoginRoot
import dev.soul.shared.navigation.Screen

@Composable
fun SetupNavGraph(startDestination: Screen = Screen.Login) {
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
        composable<Screen.Login> {
            LoginRoot(
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
//        composable<Screen.HomeGraph> {
//            HomeGraphScreen(
//                navigateToAuth = {
//                    navController.navigate(Screen.Auth) {
//                        popUpTo<Screen.HomeGraph> { inclusive = true }
//                    }
//                },
//                navigateToProfile = {
//                    navController.navigate(Screen.Profile)
//                },
//                navigateToAdminPanel = {
//                    navController.navigate(Screen.AdminPanel)
//                },
//                navigateToDetails = { productId ->
//                    navController.navigate(Screen.Details(id = productId))
//                },
//                navigateToCategorySearch = { categoryName ->
//                    navController.navigate(Screen.CategorySearch(categoryName))
//                },
//                navigateToCheckout = { totalAmount ->
//                    navController.navigate(Screen.Checkout(totalAmount))
//                }
//            )
//        }
//        composable<Screen.Profile> {
//            ProfileScreen(
//                navigateBack = {
//                    navController.navigateUp()
//                }
//            )
//        }
//        composable<Screen.AdminPanel> {
//            AdminPanelScreen(
//                navigateBack = {
//                    navController.navigateUp()
//                },
//                navigateToManageProduct = { id ->
//                    navController.navigate(Screen.ManageProduct(id = id))
//                }
//            )
//        }
//        composable<Screen.ManageProduct> {
//            val id = it.toRoute<Screen.ManageProduct>().id
//            ManageProductScreen(
//                id = id,
//                navigateBack = {
//                    navController.navigateUp()
//                }
//            )
//        }
//        composable<Screen.Details> {
//            DetailsScreen(
//                navigateBack = {
//                    navController.navigateUp()
//                }
//            )
//        }
//        composable<Screen.CategorySearch> {
//            val category = ProductCategory.valueOf(it.toRoute<Screen.CategorySearch>().category)
//            CategorySearchScreen(
//                category = category,
//                navigateToDetails = { id ->
//                    navController.navigate(Screen.Details(id))
//                },
//                navigateBack = {
//                    navController.navigateUp()
//                }
//            )
//        }
//        composable<Screen.Checkout> {
//            val totalAmount = it.toRoute<Screen.Checkout>().totalAmount
//            CheckoutScreen(
//                totalAmount = totalAmount.toDoubleOrNull() ?: 0.0,
//                navigateBack = {
//                    navController.navigateUp()
//                },
//                navigateToPaymentCompleted = { isSuccess, error ->
//                    navController.navigate(Screen.PaymentCompleted(isSuccess, error))
//                }
//            )
//        }
//        composable<Screen.PaymentCompleted> {
//            PaymentCompleted(
//                navigateBack = {
//                    navController.navigate(Screen.HomeGraph) {
//                        launchSingleTop = true
//                        // Clear backstack completely
//                        popUpTo(0) { inclusive = true }
//                    }
//                }
//            )
//        }
    }
}