package com.aura.installer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentPickDao {
    @Query("SELECT * FROM recent_picks ORDER BY pickedAt DESC LIMIT 20")
    fun getRecentPicks(): Flow<List<RecentPickEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pick: RecentPickEntity)

    @Query("DELETE FROM recent_picks WHERE uri = :uri")
    suspend fun delete(uri: String)
}
