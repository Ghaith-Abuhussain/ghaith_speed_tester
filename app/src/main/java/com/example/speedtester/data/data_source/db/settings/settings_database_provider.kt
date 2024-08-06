package com.example.speedtester.data.data_source.db.settings

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// This object is used to create the database and insert the first row in the settings table using the .addCallback function
object DatabaseProvider {
    private var INSTANCE: SettingsDatabase? = null

    fun getDatabase(context: Context): SettingsDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                SettingsDatabase::class.java,
                "settings_database"
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            getDatabase(context).settingsDao().insert(Settings(id = 1, mode = true, speedType = 1))
                        }
                    }
                })
                .build()
            INSTANCE = instance
            instance
        }
    }
}