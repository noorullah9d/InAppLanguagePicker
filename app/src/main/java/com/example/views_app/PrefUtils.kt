package com.example.views_app

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

object PrefUtils {
    private lateinit var sharedPreferences: SharedPreferences

    private const val PREFERENCE_NAME = "shared_preference"
    private const val PREFERENCE_MODE = Context.MODE_PRIVATE

    private const val FIRST_TIME_MIGRATION = "first_time_migration"
    private const val SELECTED_LANGUAGE = "selected_language"

    const val STATUS_DONE = "status_done"

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, PREFERENCE_MODE)
    }

    var firstTimeMigration
        get() = sharedPreferences.getString(FIRST_TIME_MIGRATION, "")
        set(value) = sharedPreferences.edit { putString(FIRST_TIME_MIGRATION, value) }

    var selectedLanguage
        get() = getObject(SELECTED_LANGUAGE, LanguageModel::class.java) ?: getDefaultLanguage()
        set(value) = sharedPreferences.edit { putObject(SELECTED_LANGUAGE, value) }

    private fun getDefaultLanguage(): LanguageModel {
        return LanguageModel(
            languageName = "English",
            languageLocalName = "English",
            languageCode = "en"
        )
    }

    private fun <T> getObject(key: String, classType: Class<T>): T? {
        return when (val str = sharedPreferences.getString(key, null)) {
            null -> null
            else -> Gson().fromJson(str, classType)
        }
    }

    private fun <T> putObject(key: String, value: T) {
        val editor = sharedPreferences.edit()
        editor.putString(key, Gson().toJson(value))
        editor.apply()
    }
}