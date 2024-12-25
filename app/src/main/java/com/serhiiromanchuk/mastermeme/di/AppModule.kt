package com.serhiiromanchuk.mastermeme.di

import android.content.Context
import com.serhiiromanchuk.mastermeme.presentation.core.utils.BitmapProcessor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppProvidesModule {

    @Provides
    fun provideBitmapProcessor(
        @ApplicationContext context: Context
    ): BitmapProcessor {
        return BitmapProcessor(context)
    }
}