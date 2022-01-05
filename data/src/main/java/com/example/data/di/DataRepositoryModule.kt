package com.example.data.di

import com.example.data.repository.DataRepositoryImpl
import com.example.data.repository.DataRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataRepositoryModule {

    @Binds
    abstract fun getDataRepository(repo: DataRepositoryImpl): DataRepository
}

@InstallIn(ViewModelComponent::class)
@Module
class DataRepositoryModuleInternal {

    @Provides
    fun getTestingVal(): Boolean = false
}