package worklogtracker.frontend.presentation.framework.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import worklogtracker.frontend.presentation.framework.annotations.Email
import worklogtracker.frontend.presentation.framework.annotations.Required
import kotlin.reflect.full.memberProperties

// TODO dit effe chekcen
abstract class BaseViewModel<S : BaseUiState>(
    private val initialState: S,
) : ViewModel() {
    protected var _uiState by mutableStateOf(initialState)
    val uiState: S get() = _uiState

    protected fun resetState(transform: (S) -> S = { initialState }) {
        _uiState = transform(initialState)
    }

    fun <T> updateState(update: S.(T) -> S): (T) -> Unit =
        { value ->
            _uiState = uiState.update(value)
        }

    fun <T : BaseUiState> T.validate(setError: (String) -> Unit): Boolean {
        val kClass = this::class
        for (property in kClass.memberProperties) {
            val value = property.call(this)
            property.annotations.filterIsInstance<Required>().forEach { required ->
                if (value is String && value.isBlank()) {
                    setError(required.message)
                    return false
                }
            }

            property.annotations.filterIsInstance<Email>().forEach { email ->
                if (value is String &&
                    !Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")
                        .matches(value)
                ) {
                    setError(email.message)
                    return false
                }
            }
        }
        return true
    }

    protected fun launchWithErrorHandling(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                setLoading(true)
                block()
            } catch (e: Exception) {
                val message =
                    when {
                        e.message?.contains("401") == true -> "Ongeldig e-mailadres of wachtwoord."
                        e.message?.contains("403") == true -> "Geen toegang."
                        e.message?.contains("Unable to resolve host") == true -> "Geen internetverbinding."
                        else -> e.message ?: "Er is iets misgegaan"
                    }
                setError(message)
            } finally {
                setLoading(false)
            }
        }
    }

    protected abstract fun setLoading(value: Boolean)

    protected abstract fun setError(message: String?)
}
