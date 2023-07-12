package com.gigih.android.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.core.os.ConfigurationCompat
import java.util.Locale

class LanguageHelper(context: Context) : ContextWrapper(context) {

    fun updateLocale(c: Context, language: String): ContextWrapper {
        var context = c
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        val localeList = LocaleList(Locale(language))
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            context = context.createConfigurationContext(configuration)
        } else {
            @Suppress("DEPRECATION")
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        return LanguageHelper(context)
    }

    fun applyLocalizedContext(context: Context, prefLocaleCode: String, onConfig: (config: Configuration) -> Unit) {
        val currentLocale = getLocaleFromPrefCode(prefLocaleCode)
        val baseLocale = getLocaleFromConfiguration(context.resources.configuration)
        Locale.setDefault(currentLocale)
        if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) {
            val config = getLocalizedConfiguration(currentLocale)
            @Suppress("DEPRECATION")
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }

        val locale = Locale("in")
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        val resources = context.resources

        @Suppress("DEPRECATION")
        resources?.updateConfiguration(config, resources.displayMetrics)
        onConfig(config)
    }

    private fun getLocaleFromPrefCode(prefCode: String): Locale {
        val supportedLocales = listOf("en", "id")
        val localeCode = if(prefCode != "sys_def") {
            prefCode
        } else {
            val systemLang = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)?.language
            if(systemLang in supportedLocales){
                systemLang ?: "en"
            } else {
                "en"
            }
        }
        return Locale(localeCode)
    }

    @Suppress("DEPRECATION")
    private fun getLocaleFromConfiguration(configuration: Configuration): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            configuration.locales.get(0)
        } else {
            configuration.locale
        }
    }

    private fun getLocalizedConfiguration(locale: Locale): Configuration {
        val config = Configuration()
        return config.apply {
            config.setLayoutDirection(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                config.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
            } else {
                config.setLocale(locale)
            }
        }
    }
}