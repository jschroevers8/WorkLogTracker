package worklogtracker.data.auth


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_prefs")

class AuthManager(context: Context) : AuthManagerInterface {

    private val dataStore = context.dataStore

    companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val EMAIL = stringPreferencesKey("email")
        val ROLE = stringPreferencesKey("role")
    }

    override val authTokenFlow: Flow<String?> = dataStore.data.map { prefs ->
        prefs[AUTH_TOKEN]
    }

    override val userDataFlow: Flow<UserData?> = dataStore.data.map { prefs ->
        val firstName = prefs[FIRST_NAME] ?: return@map null
        val lastName = prefs[LAST_NAME] ?: ""
        val email = prefs[EMAIL] ?: ""
        val role = prefs[ROLE] ?: ""
        UserData(firstName, lastName, email, role)
    }

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { prefs ->
            prefs[AUTH_TOKEN] = token
        }
    }

    override suspend fun saveUserData(userData: UserData) {
        dataStore.edit { prefs ->
            prefs[FIRST_NAME] = userData.firstName
            prefs[LAST_NAME] = userData.lastName
            prefs[EMAIL] = userData.email
            prefs[ROLE] = userData.role
        }
    }

    override suspend fun clearAuthToken() {
        dataStore.edit { prefs ->
            prefs.remove(AUTH_TOKEN)
        }
    }

    override suspend fun clearUserData() {
        dataStore.edit { prefs ->
            prefs.remove(FIRST_NAME)
            prefs.remove(LAST_NAME)
            prefs.remove(EMAIL)
            prefs.remove(ROLE)
        }
    }
}
