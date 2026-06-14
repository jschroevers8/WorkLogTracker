package worklogtracker.frontend.data.auth

import kotlinx.coroutines.flow.Flow

interface AuthManagerInterface {
    val authTokenFlow: Flow<String?>
    val userDataFlow: Flow<UserData?>

    suspend fun saveAuthToken(token: String)

    suspend fun saveUserData(userData: UserData)

    suspend fun clearAuthToken()

    suspend fun clearUserData()
}
