package com.rubenquadros.epicarticles.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rubenquadros.epicarticles.data.local.dao.WikiDAO
import com.rubenquadros.epicarticles.data.local.entity.WikiEntity

@Database(entities = [WikiEntity::class], version = 1)
abstract class WikiDatabase: RoomDatabase() {
    abstract fun wikiDAO(): WikiDAO
}