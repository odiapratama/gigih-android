package com.gigih.android.di

import android.content.Context
import com.gigih.android.data.database.AppDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Database {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): AppDataStore {
        return AppDataStore(context)
    }
}