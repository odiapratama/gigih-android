package com.gigih.android.data.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(
    private val context: Context
) {
    companion object {
        const val DATASTORE_NAME = "settings"

        private val SHOW_ACTIVITY_LIFECYCLE = booleanPreferencesKey("show_activity_lifecycle")
        private val SHOW_FRAGMENT_LIFECYCLE = booleanPreferencesKey("show_fragment_lifecycle")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE_NAME)

    suspend fun setShowActivityLifecycle(isShowing: Boolean) {
        context.dataStore.edit {
            it[SHOW_ACTIVITY_LIFECYCLE] = isShowing
        }
    }

    fun getShowActivityLifecycle(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[SHOW_ACTIVITY_LIFECYCLE] ?: false
        }
    }

    suspend fun setShowFragmentLifecycle(isShowing: Boolean) {
        context.dataStore.edit {
            it[SHOW_FRAGMENT_LIFECYCLE] = isShowing
        }
    }

    fun getShowFragmentLifecycle(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[SHOW_FRAGMENT_LIFECYCLE] ?: false
        }
    }
}