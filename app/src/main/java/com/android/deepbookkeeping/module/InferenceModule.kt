package com.android.deepbookkeeping.module

import android.content.Context
import com.android.deepbookkeeping.llm_inference.InferenceModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InferenceModule {
    @Provides
    @Singleton
    fun provideInferenceModel(@ApplicationContext context: Context): InferenceModel {
        return InferenceModel.getInstance(context)
    }
}