package com.aura.installer.di

import com.aura.installer.domain.installer.Installer
import com.aura.installer.domain.installer.UserConfirmInstaller
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InstallerModule {

    @Binds
    @Singleton
    abstract fun bindInstaller(impl: UserConfirmInstaller): Installer
}
