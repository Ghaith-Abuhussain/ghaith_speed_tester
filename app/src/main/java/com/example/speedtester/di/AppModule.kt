package com.example.speedtester.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.speedtester.data.data_source.api.SpeedTestService
import com.example.speedtester.data.data_source.db.settings.Settings
import com.example.speedtester.data.data_source.db.settings.SettingsDao
import com.example.speedtester.data.data_source.db.settings.SettingsDatabase
import com.example.speedtester.data.repository.SettingsDatabaseRepositoryImpl
import com.example.speedtester.data.repository.SpeedTestRepositoryImpl
import com.example.speedtester.domain.repository.SettingsDatabaseRepository
import com.example.speedtester.domain.repository.SpeedTestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): SettingsDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            SettingsDatabase::class.java,
            "settings_database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        provideAppDatabase(appContext)
                            .settingsDao().insert(Settings(id = 1, mode = false, speedType = 1))
                    }
                }
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideSettingDao(settingsDatabase: SettingsDatabase): SettingsDao {
        return settingsDatabase.settingsDao()
    }

    @Provides
    @Singleton
    fun provideApiService(): SpeedTestService {
        return Retrofit.Builder()
            .baseUrl("https://file.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpeedTestService::class.java)
    }

    @Provides
    @Singleton
    fun provideSettingsDatabaseRepository(settingsDao: SettingsDao) : SettingsDatabaseRepository {
        return SettingsDatabaseRepositoryImpl(settingsDao)
    }

    @Provides
    @Singleton
    fun provideSpeedTestRepositoryImpl(service: SpeedTestService): SpeedTestRepository {
        return SpeedTestRepositoryImpl(service)
    }
}