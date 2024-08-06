package com.example.speedtester.domain.repository

import androidx.lifecycle.LiveData
import com.example.speedtester.data.data_source.db.settings.Settings


interface SettingsDatabaseRepository {

    suspend fun insert(settings: Settings)

    fun getSettingsById(id: Int): LiveData<Settings?>

    suspend fun updateMode(id: Int, mode: Boolean)

    suspend fun updateSpeedType(id: Int, speedType: Int)

}