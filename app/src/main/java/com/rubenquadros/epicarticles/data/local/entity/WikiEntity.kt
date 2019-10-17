package com.rubenquadros.epicarticles.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles_data")
data class WikiEntity(
    @PrimaryKey
    var id: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "description")
    var description: String?,

    @ColumnInfo(name = "image")
    var image: String?,

    @ColumnInfo(name = "url")
    var url: String?
) {
}