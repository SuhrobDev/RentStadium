package dev.soul.rentstadium

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.soul.datastore.datastore.DataStoreRepository
import dev.soul.datastore.datastore.PreferencesKeys
import dev.soul.navigation.SetupNavGraph
import dev.soul.shared.navigation.Screen
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
//        val customerRepository = koinInject<DataStoreRepository>()
        var appReady by remember { mutableStateOf(false) }

        val isUserAuthenticated = remember { mutableStateOf(false) }
        val startDestination = remember {
            if (false) Screen.Base
            else Screen.Login
        }

        LaunchedEffect(Unit) {
//            isUserAuthenticated.value =
//                (customerRepository.getData(PreferencesKeys.REFRESH_TOKEN, "").first()).isNotEmpty()


            appReady = true
        }

        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = appReady
        ) {
            SetupNavGraph(
                startDestination = startDestination
            )
        }
    }
}