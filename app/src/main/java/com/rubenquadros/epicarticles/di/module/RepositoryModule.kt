package com.rubenquadros.epicarticles.di.module

import com.rubenquadros.epicarticles.data.local.dao.WikiDAO
import com.rubenquadros.epicarticles.data.remote.api.WikipediaApi
import com.rubenquadros.epicarticles.data.repository.WikiRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideWikiRepository(wikiDAO: WikiDAO, wikiApi: WikipediaApi): WikiRepo {
        return WikiRepo(wikiDAO, wikiApi)
    }
}