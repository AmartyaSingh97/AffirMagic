package com.example.affirmagic.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.affirmagic.db.dao.AffirmationsDao
import com.example.affirmagic.db.entity.AffirmationsEntity


@Database(entities = [AffirmationsEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun AffirmationsDao(): AffirmationsDao

     companion object {
         @Volatile private var instance: com.example.affirmagic.db.database.Database? = null

         fun getDatabase(context: Context): com.example.affirmagic.db.database.Database =
             instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

         private fun buildDatabase(appContext: Context) =
             androidx.room.Room.databaseBuilder(
                 appContext,
                 com.example.affirmagic.db.database.Database::class.java, "affirmations-db"
             ).fallbackToDestructiveMigration().build()
     }
}

