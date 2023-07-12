package com.gigih.android.data.database

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class AppPreferences(
    context: Context
) {
    companion object {
        const val PREFERENCES_NAME = "app_preferences"
        const val THEME_MODE = "theme_mode"
        const val LANGUAGE = "language"
        const val DEFAULT_LANGUAGE = "en"
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    var themeMode: Int
        get() = sharedPreferences.getInt(THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        set(value) = editor.putInt(THEME_MODE, value).apply()

    var language: String
        get() = sharedPreferences.getString(LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        set(value) = editor.putString(LANGUAGE, value).apply()
}