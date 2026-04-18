package com.aura.installer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_picks")
data class RecentPickEntity(
    @PrimaryKey val uri: String,
    val displayName: String,
    val pickedAt: Long = System.currentTimeMillis(),
)
