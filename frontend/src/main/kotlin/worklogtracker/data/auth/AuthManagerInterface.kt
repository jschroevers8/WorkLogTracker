package worklogtracker.data.auth

import kotlinx.coroutines.flow.Flow

interface AuthManagerInterface {
    val authTokenFlow: Flow<String?>
    suspend fun saveAuthToken(token: String)
    suspend fun clearAuthToken()
}