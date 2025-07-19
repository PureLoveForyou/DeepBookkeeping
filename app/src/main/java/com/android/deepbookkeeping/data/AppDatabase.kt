package com.android.deepbookkeeping.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.constants.DefaultValues
import com.android.deepbookkeeping.data.local.dao.AppDao
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.data.local.entity.Transaction

@Database(
    entities = [Transaction::class, Category::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        private const val TAG = Constants.TAG_PREFIX + "AppDatabase"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "accounting_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // TODO: 初始化默认数据
                        initDefaultData(db)
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        private fun initDefaultData(db: SupportSQLiteDatabase) {
            Log.d(TAG, "initDefaultData: ${DefaultValues.defaultCategories}")
            DefaultValues.defaultCategories.forEach { category: Category ->
                db.execSQL(
                    """
                        INSERT INTO categories (id, name, type, isDefault, iconResourceId)
                        VALUES (?, ?, ?, ?, ?)
                    """.trimIndent(),
                    arrayOf(
                        category.id,
                        category.name,
                        category.type,
                        category.isDefault,
                        category.iconResourceId
                    )
                )
            }
        }
    }
}