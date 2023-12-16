package com.example.affirmagic.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.affirmagic.db.entity.AffirmationsEntity

@Dao
interface AffirmationsDao {

    @Query("SELECT * FROM affirmations where date = :date")
    fun getAffirmations(date: String): LiveData<List<AffirmationsEntity>>

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAffirmations(affirmationsEntity: AffirmationsEntity): Long
}