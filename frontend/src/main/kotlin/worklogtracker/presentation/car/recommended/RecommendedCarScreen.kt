package worklogtracker.presentation.car.recommended

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.koin.androidx.compose.koinViewModel
import worklogtracker.plugins.navigation.Screen
import worklogtracker.presentation.framework.components.RmcScreen
import worklogtracker.presentation.framework.components.button.RmcPrimaryButton

@Composable
fun RecommendedCarScreen(backStack: NavBackStack<NavKey>) {
    val viewModel: RecommendedCarViewModel = koinViewModel()
    val state = viewModel.uiState

    RmcScreen(backStack = backStack) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                state.lastQuestion?.let { question ->
                    item {
                        ChatBubble(
                            message = ChatMessage(
                                text = question,
                                isUser = true
                            )
                        )
                    }
                }

                state.recommendedMessage?.let { message ->
                    item {
                        ChatBubble(
                            message = ChatMessage(
                                text = message,
                                isUser = false
                            )
                        )
                    }

                    state.carId?.let { carId ->
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            RmcPrimaryButton(
                                text = "View recommended car",
                                onClick = {
                                    backStack.add(
                                        Screen.ShowAdvertisement(carId)
                                    )
                                }
                            )
                        }
                    }
                }

                state.error?.let { error ->
                    item {
                        ChatBubble(
                            message = ChatMessage(
                                text = error,
                                isUser = false
                            )
                        )
                    }
                }

                if (state.loading) {
                    item {
                        ChatBubble(
                            message = ChatMessage(
                                text = "Typing...",
                                isUser = false
                            )
                        )
                    }
                }
            }

            ChatInputField(
                value = state.inputText,
                loading = state.loading,
                onValueChange = { viewModel.onInputChanged(it) },
                onSend = { viewModel.getRecommendedCar() }
            )
        }
    }
}
