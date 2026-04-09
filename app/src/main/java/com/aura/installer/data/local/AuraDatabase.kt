package com.aura.installer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecentPickEntity::class], version = 1, exportSchema = false)
abstract class AuraDatabase : RoomDatabase() {
    abstract fun recentPickDao(): RecentPickDao
}
