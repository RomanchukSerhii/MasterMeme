package com.serhiiromanchuk.mastermeme.di

import android.content.Context
import androidx.room.Room
import com.serhiiromanchuk.mastermeme.data.database.MemeDao
import com.serhiiromanchuk.mastermeme.data.database.MemeDatabase
import com.serhiiromanchuk.mastermeme.data.repository.MemeDbRepositoryImpl
import com.serhiiromanchuk.mastermeme.domain.rejpository.MemeDbRepository
import com.serhiiromanchuk.mastermeme.utils.BitmapProcessor
import com.serhiiromanchuk.mastermeme.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProvidesModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MemeDatabase {
        return Room.databaseBuilder(
            context,
            MemeDatabase::class.java,
            Constants.MEME_DB
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlarmDao(database: MemeDatabase) = database.getMemeDao()

    @Provides
    @Singleton
    fun provideBitmapProcessor(
        @ApplicationContext context: Context
    ): BitmapProcessor {
        return BitmapProcessor(context)
    }

    @Provides
    fun provideMemeDbRepository(
        memeDao: MemeDao,
        bitmapProcessor: BitmapProcessor
    ): MemeDbRepository {
        return MemeDbRepositoryImpl(memeDao, bitmapProcessor)
    }
}