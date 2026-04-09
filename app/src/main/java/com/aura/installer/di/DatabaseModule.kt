package com.aura.installer.di

import android.content.Context
import androidx.room.Room
import com.aura.installer.data.local.AuraDatabase
import com.aura.installer.data.local.RecentPickDao
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
    fun provideAuraDatabase(@ApplicationContext context: Context): AuraDatabase =
        Room.databaseBuilder(context, AuraDatabase::class.java, "aura.db").build()

    @Provides
    fun provideRecentPickDao(db: AuraDatabase): RecentPickDao = db.recentPickDao()
}
