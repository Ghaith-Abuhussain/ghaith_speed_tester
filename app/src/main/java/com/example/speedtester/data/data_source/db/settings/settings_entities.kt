package com.example.speedtester.data.data_source.db.settings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// this is the entity that represents the settings table
@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "mode") val mode: Boolean,
    @ColumnInfo(name = "speed_type") val speedType: Int
)