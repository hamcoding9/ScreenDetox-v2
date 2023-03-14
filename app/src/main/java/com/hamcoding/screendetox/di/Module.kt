package com.hamcoding.screendetox.di

import com.hamcoding.screendetox.data.firebase.source.ApiClient
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Singleton
    @Provides
    fun provideApiClient(): ApiClient {
        return ApiClient.create()
    }
}