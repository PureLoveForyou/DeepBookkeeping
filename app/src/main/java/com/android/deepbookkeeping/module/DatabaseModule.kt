package com.android.deepbookkeeping.module

import android.content.Context
import androidx.room.Room
import com.android.deepbookkeeping.data.AppDatabase
import com.android.deepbookkeeping.data.local.dao.AppDao
import com.android.deepbookkeeping.data.repository.AccountingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideAppDao(database: AppDatabase): AppDao {
        return database.appDao()
    }

    @Provides
    @Singleton
    fun provideAccountingRepository(appDao: AppDao): AccountingRepository {
        return AccountingRepository(appDao)
    }
}