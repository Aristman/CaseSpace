package ru.marslab.casespace.data

import android.content.SharedPreferences
import ru.marslab.casespace.BuildConfig
import ru.marslab.casespace.R
import ru.marslab.casespace.domain.repository.Constant
import ru.marslab.casespace.domain.repository.Storage

class StorageImpl(
    private val sharedPreferences: SharedPreferences
) : Storage {
    override fun getNasaApikey(): String = BuildConfig.NASA_APIKEY

    override fun saveTheme(theme: Int) {
        sharedPreferences.edit()
            .putInt(Constant.SETTING_THEME, theme)
            .apply()
    }

    override fun getTheme(): Int =
        sharedPreferences.getInt(Constant.SETTING_THEME, R.style.Theme_CaseSpace)
}