package com.serhiiromanchuk.mastermeme.di

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.serhiiromanchuk.mastermeme.data.database.MemeDao
import com.serhiiromanchuk.mastermeme.data.database.MemeDatabase
import com.serhiiromanchuk.mastermeme.data.repository.MemeDbRepositoryImpl
import com.serhiiromanchuk.mastermeme.domain.rejpository.MemeDbRepository
import com.serhiiromanchuk.mastermeme.utils.ImageProcessor
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
    ): ImageProcessor {
        return ImageProcessor(context)
    }

    @Provides
    @Singleton
    fun provideContentResolver(
        @ApplicationContext context: Context
    ): ContentResolver {
        return context.contentResolver
    }

    @Provides
    fun provideMemeDbRepository(
        memeDao: MemeDao,
        imageProcessor: ImageProcessor,
        contentResolver: ContentResolver
    ): MemeDbRepository {
        return MemeDbRepositoryImpl(memeDao, imageProcessor, contentResolver)
    }
}