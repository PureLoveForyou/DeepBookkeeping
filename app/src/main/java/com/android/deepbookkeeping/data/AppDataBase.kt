package com.android.deepbookkeeping.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.deepbookkeeping.data.local.dao.AppDao
import com.android.deepbookkeeping.data.local.entity.Transaction
import com.android.deepbookkeeping.data.local.entity.User

@Database(
    entities = [User::class, Transaction::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "accounting_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // TODO: 初始化默认数据
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}