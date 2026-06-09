package worklogtracker.presentation.car.recommended

import worklogtracker.presentation.framework.viewmodel.BaseUiState

data class RecommendedCarUiState(
    override val loading: Boolean = false,
    override val error: String? = null,
    val inputText: String = "",
    val lastQuestion: String? = null,
    val recommendedMessage: String? = null,
    val carId: Int? = null
) : BaseUiState

