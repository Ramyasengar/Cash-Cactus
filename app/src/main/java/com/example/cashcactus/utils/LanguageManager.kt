package com.example.cashcactus.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

object LanguageManager {
    private const val SETTINGS_PREF = "app_settings"
    private const val KEY_SELECTED_LANGUAGE = "selected_language"

    const val ENGLISH = "en"
    const val HINDI = "hi"

    fun getSavedLanguage(context: Context): String {
        val pref = context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE)
        return pref.getString(KEY_SELECTED_LANGUAGE, ENGLISH) ?: ENGLISH
    }

    fun setLanguage(context: Context, languageCode: String) {
        context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_SELECTED_LANGUAGE, languageCode)
            .apply()

        val locales = if (languageCode == ENGLISH) {
            LocaleListCompat.getEmptyLocaleList()
        } else {
            LocaleListCompat.forLanguageTags(languageCode)
        }
        AppCompatDelegate.setApplicationLocales(locales)
    }

    fun applySavedLanguage(context: Context) {
        setLanguage(context, getSavedLanguage(context))
    }
}
