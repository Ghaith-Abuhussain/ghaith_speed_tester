package com.example.speedtester.data.repository

import androidx.lifecycle.LiveData
import com.example.speedtester.data.data_source.db.settings.Settings
import com.example.speedtester.data.data_source.db.settings.SettingsDao
import com.example.speedtester.domain.repository.SettingsDatabaseRepository

// this is the implementation of the SettingsDatabaseRepository to insert/update the data in the settings table in the database
class SettingsDatabaseRepositoryImpl(private val settingsDao: SettingsDao) :
    SettingsDatabaseRepository {
    override suspend fun insert(settings: Settings) {
        settingsDao.insert(settings)
    }

    override fun getSettingsById(id: Int): LiveData<Settings?> {
        return settingsDao.getSettingsById(id)
    }

    override suspend fun updateMode(id: Int, mode: Boolean) {
        settingsDao.updateMode(id, mode)
    }

    override suspend fun updateSpeedType(id: Int, speedType: Int) {
        settingsDao.updateSpeedType(id, speedType)
    }
}
