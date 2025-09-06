package dev.soul.validation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.soul.shared.Resources
import dev.soul.shared.components.BaseBox
import dev.soul.shared.navigation.Screen
import dev.soul.shared.utils.Logger
import dev.soul.shared.utils.UiEvent
import dev.soul.validation.ValidationEvent
import dev.soul.validation.ValidationState
import dev.soul.validation.ValidationViewModel
import dev.soul.validation.components.SessionExpiredDialog
import org.jetbrains.compose.resources.painterResource

@Composable
fun ValidationRoot(
    modifier: Modifier = Modifier,
    viewModel: ValidationViewModel,
    onNavigate: (Screen) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect( viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString())
                }

                else -> Unit
            }
        }
    }

    if (state.shouldLogout) {
        SessionExpiredDialog(onRelogin = {
            viewModel.onEvent(ValidationEvent.ShowLogout(false))
        })
    }

    Content(
        modifier = modifier,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    state: ValidationState,
    onEvent: (ValidationEvent) -> Unit,
) {
    BaseBox(isLoading = state.isLoading) {
        Image(
            painter = painterResource(Resources.Image.EmptySchedule),
            contentDescription = null,
            modifier = modifier.align(
                Alignment.Center
            ).padding(bottom = 72.dp)
        )
    }
}