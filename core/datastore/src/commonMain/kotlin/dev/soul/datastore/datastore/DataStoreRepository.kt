package dev.soul.datastore.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.text.get

class DataStoreRepository(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveData(param: String, data: Int = 0) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(param)] = data
        }
    }

    suspend fun saveData(param: String, data: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(param)] = data
        }
    }

    suspend fun saveData(param: String, data: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(param)] = data
        }
    }

    suspend fun saveData(param: String, data: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(param)] = data
        }
    }

    suspend fun saveData(param: String, data: Float) {
        dataStore.edit { preferences ->
            preferences[floatPreferencesKey(param)] = data
        }
    }

    suspend fun saveData(param: String, data: Double) {
        dataStore.edit { preferences ->
            preferences[doublePreferencesKey(param)] = data
        }
    }

    fun getData(param: String, default: Int = 0) =
        dataStore.data.map { preferences ->
            preferences[intPreferencesKey(param)] ?: default
        }

    fun getData(param: String, default: Long = 0L) =
        dataStore.data.map { preferences ->
            preferences[longPreferencesKey(param)] ?: default
        }

    fun getData(param: String, default: Boolean = false) =
        dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(param)] ?: default
        }

    fun getData(param: String, default: Float = 0.0f) =
        dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(param)] ?: default
        }

    fun getData(param: String, default: Double = 0.0) =
        dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(param)] ?: default
        }

    fun getData(param: String, default: String = ""): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(param)] ?: default
        }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}