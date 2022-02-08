package com.example.fastplaylistbuilder.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_movies")
data class MovieEntity (
        @PrimaryKey(autoGenerate = false)
        var title: String,
        @ColumnInfo(name = "year")
        var year: String? = null,
        @ColumnInfo(name = "rating")
        var rating: String? = null,
        @ColumnInfo(name = "poster")
        var poster: String? = null
)