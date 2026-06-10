package worklogtracker.data.auth

import kotlinx.coroutines.flow.Flow

interface AuthManagerInterface {
    val authTokenFlow: Flow<String?>
    val userDataFlow: Flow<UserData?>
    suspend fun saveAuthToken(token: String)
    suspend fun saveUserData(userData: UserData)
    suspend fun clearAuthToken()
    suspend fun clearUserData()
}

data class UserData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: String
)