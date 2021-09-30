package ru.marslab.casespace.data

import android.content.SharedPreferences
import ru.marslab.casespace.BuildConfig
import ru.marslab.casespace.R
import ru.marslab.casespace.data.mapper.toDomain
import ru.marslab.casespace.data.room.CaseSpaceDatabase
import ru.marslab.casespace.domain.model.Note
import ru.marslab.casespace.domain.repository.Constant
import ru.marslab.casespace.domain.repository.Storage

class StorageImpl(
    private val sharedPreferences: SharedPreferences,
    private val database: CaseSpaceDatabase
) : Storage {
    override fun getNasaApikey(): String = BuildConfig.NASA_APIKEY

    override fun saveTheme(theme: Int) {
        sharedPreferences.edit()
            .putInt(Constant.SETTING_THEME, theme)
            .apply()
    }

    override fun getTheme(): Int =
        sharedPreferences.getInt(Constant.SETTING_THEME, R.style.Theme_CaseSpace)

    override suspend fun getAllNotes(): List<Note> =
        database.noteDao().getAllNotes().map { it.toDomain() }

}