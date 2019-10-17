package com.rubenquadros.epicarticles.di.module

import android.app.Application
import androidx.room.Room
import com.rubenquadros.epicarticles.data.local.dao.WikiDAO
import com.rubenquadros.epicarticles.data.local.database.WikiDatabase
import dagger.Module
import dagger.Provides

@Module
class DbModule(private val application: Application) {
    @Provides
    fun providePlacesDatabase(): WikiDatabase {
        return Room.databaseBuilder(application, WikiDatabase::class.java, "Articles.db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providePlacesDAO(wikiDatabase: WikiDatabase): WikiDAO {
        return wikiDatabase.wikiDAO()
    }
}