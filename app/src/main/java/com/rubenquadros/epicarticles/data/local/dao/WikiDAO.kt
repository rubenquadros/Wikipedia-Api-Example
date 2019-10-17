package com.rubenquadros.epicarticles.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rubenquadros.epicarticles.data.local.entity.WikiEntity

@Dao
interface WikiDAO {

    @Insert
    fun insertAll(data: ArrayList<WikiEntity>)

    @Query("DELETE FROM articles_data")
    fun deleteAll()

    @Query("SELECT * FROM articles_data")
    fun getArticles(): List<WikiEntity>
}