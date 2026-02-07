package ru.sicampus.bootcamp2026.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

object DataStoreManager {
    private lateinit var dataStore: DataStore<Preferences>

    private val EMAIL_KEY = stringPreferencesKey("email")
    private val PASSWORD_KEY = stringPreferencesKey("password")
    private val USER_ID_KEY = stringPreferencesKey("user_id")

    fun init(context: Context) {
        dataStore = context.dataStore
    }

    suspend fun saveCredentials(email: String, password: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
            preferences[PASSWORD_KEY] = password
        }
    }

    suspend fun getCredentials(): Pair<String, String>? {
        val email = dataStore.data.map { it[EMAIL_KEY] }.first()
        val password = dataStore.data.map { it[PASSWORD_KEY] }.first()
        return if (email != null && password != null) Pair(email, password) else null
    }

    suspend fun saveUserId(userId: Long) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId.toString()
        }
    }

    suspend fun getUserId(): Long? {
        val userId = dataStore.data.map { it[USER_ID_KEY] }.first()
        return userId?.toLongOrNull()
    }

    suspend fun clearCredentials() {
        dataStore.edit { preferences ->
            preferences.remove(EMAIL_KEY)
            preferences.remove(PASSWORD_KEY)
            preferences.remove(USER_ID_KEY)
        }
    }
}