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
    }

    override val authTokenFlow: Flow<String?> = dataStore.data.map { prefs ->
        prefs[AUTH_TOKEN]
    }

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { prefs ->
            prefs[AUTH_TOKEN] = token
        }
    }

    override suspend fun clearAuthToken() {
        dataStore.edit { prefs ->
            prefs.remove(AUTH_TOKEN)
        }
    }
}
