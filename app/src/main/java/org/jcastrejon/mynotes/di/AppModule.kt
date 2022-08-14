package org.jcastrejon.mynotes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.jcastrejon.arch.DefaultDispatcherProvider
import org.jcastrejon.arch.DispatcherProvider

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provider(): DispatcherProvider = DefaultDispatcherProvider()
}