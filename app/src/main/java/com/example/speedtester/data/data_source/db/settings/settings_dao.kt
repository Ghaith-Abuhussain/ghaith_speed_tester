package com.example.speedtester.data.data_source.db.settings

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// This is the Dao to manipulate the data in the app database
@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Query("SELECT * FROM settings WHERE id = :id")
    fun getSettingsById(id: Int): LiveData<Settings?>

    @Query("UPDATE settings SET mode = :mode WHERE id = :id")
    suspend fun updateMode(id: Int, mode: Boolean)

    @Query("UPDATE settings SET speed_type = :speedType WHERE id = :id")
    suspend fun updateSpeedType(id: Int, speedType: Int)
}