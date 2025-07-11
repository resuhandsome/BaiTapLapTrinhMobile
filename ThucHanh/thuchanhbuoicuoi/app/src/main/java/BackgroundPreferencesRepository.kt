package com.example.myapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class BackgroundPreferencesRepository(private val context: Context) {

    companion object {
        private val BACKGROUND_COLOR_KEY = stringPreferencesKey("background_color")
        const val WHITE = "white"
        const val BLACK = "black"
        const val PINK = "pink"
    }

    val backgroundColorFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[BACKGROUND_COLOR_KEY] ?: WHITE
        }

    suspend fun saveBackgroundColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[BACKGROUND_COLOR_KEY] = color
        }
    }
}
