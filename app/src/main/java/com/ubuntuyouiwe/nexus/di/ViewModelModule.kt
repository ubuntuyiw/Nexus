package com.ubuntuyouiwe.nexus.di

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideTextToSpeech(@ApplicationContext context: Context): TextToSpeech {
        return TextToSpeech(context) { status ->
            if (status == TextToSpeech.ERROR) {
                Toast.makeText(context, "TextToSpeech Error", Toast.LENGTH_LONG).show()
            }
        }
    }
}