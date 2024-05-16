package com.example.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.common.Constantes
import com.example.fitgo.data.retrofit.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreTokens(private val context: Context) {
    companion object{
        private val accessToken = stringPreferencesKey(Constantes.ACCESSTOKEN)
        private val refreshToken = stringPreferencesKey(Constantes.REFRESHTOKEN)
    }

    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map {
                preferences ->
            preferences[accessToken]
        }
    }


    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[accessToken] = token
        }
    }


    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[refreshToken]
        }
    }

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[refreshToken] = token
        }
    }
}