package com.calyrsoft.ucbp1.features.login.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "user_preferences")

class LoginDataStore(
    private val context: Context
) {
    companion object {
        private val USER_NAME = stringPreferencesKey("user_name")
        private val TOKEN = stringPreferencesKey("token")
        private val USER_ID = stringPreferencesKey("user_id")
    }

    // Guardar username
    suspend fun saveUserName(userName: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }

    // Obtener username
    suspend fun getUserName(): Result<String> {
        val preferences = context.dataStore.data.first()
        return if (preferences[USER_NAME] != null) {
            Result.success(preferences[USER_NAME]!!)
        } else {
            Result.failure(Exception("User name not found"))
        }
    }

    // Guardar token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    // Obtener token
    suspend fun getToken(): Result<String> {
        val preferences = context.dataStore.data.first()
        return if (preferences[TOKEN] != null) {
            Result.success(preferences[TOKEN]!!)
        } else {
            Result.failure(Exception("Token not found"))
        }
    }

    // Guardar userId
    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    // Obtener userId
    suspend fun getUserId(): Result<String> {
        val preferences = context.dataStore.data.first()
        return if (preferences[USER_ID] != null) {
            Result.success(preferences[USER_ID]!!)
        } else {
            Result.failure(Exception("User ID not found"))
        }
    }

    // Guardar toda la sesión de una vez
    suspend fun saveSession(userName: String, token: String, userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
            preferences[TOKEN] = token
            preferences[USER_ID] = userId
        }
    }

    // Verificar si hay sesión activa
    suspend fun isLoggedIn(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[TOKEN] != null
    }

    // Limpiar toda la sesión (logout)
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}