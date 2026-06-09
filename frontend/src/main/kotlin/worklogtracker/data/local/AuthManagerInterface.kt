package worklogtracker.data.local

import kotlinx.coroutines.flow.Flow

interface AuthManagerInterface {
    val authTokenFlow: Flow<String?>
    suspend fun saveAuthToken(token: String)
    suspend fun clearAuthToken()
}