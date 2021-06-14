package com.irfanirawansukirman.network.di

import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.irfanirawansukirman.network.dao.MovieDao
import com.irfanirawansukirman.network.factory.CacheFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class CacheModule {

    @Singleton
    @Provides
    fun provideCacheFactory(application: Application): CacheFactory = Room
        .databaseBuilder(application, CacheFactory::class.java, "db_kita_movie")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideMovieDao(cacheFactory: CacheFactory): MovieDao = cacheFactory.movieDao()

    @Singleton
    @Provides
    fun provideSecretPreference(application: Application): SharedPreferences {
        val masterKey = MasterKey.Builder(application)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            application,
            "my_secret_preference",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}