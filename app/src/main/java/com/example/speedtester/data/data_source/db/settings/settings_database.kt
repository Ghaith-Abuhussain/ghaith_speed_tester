package com.example.speedtester.data.data_source.db.settings

import androidx.room.Database
import androidx.room.RoomDatabase

// this is the class that represent the app database (we specify the entities:"tables" and the daos:"to manipulate the data int the tables")
@Database(entities = [Settings::class], version = 1)
abstract class SettingsDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao
}