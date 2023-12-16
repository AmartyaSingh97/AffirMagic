package com.example.affirmagic.db.entity

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "affirmations")

data class AffirmationsEntity(
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "author")
    val author: String,
    @PrimaryKey
    val uniqueId: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "dzType")
    val dzType: String,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "bgImageUrl")
    val bgImageUrl: String,
    @ColumnInfo(name = "theme")
    val theme: String,
    @ColumnInfo(name = "themeTitle")
    val themeTitle: String,
    @ColumnInfo(name = "articleUrl")
    val articleUrl: String,
    @ColumnInfo(name = "dzImageUrl")
    val dzImageUrl: String,
    @ColumnInfo(name = "primaryCTAText")
    val primaryCTAText: String,
    @ColumnInfo(name = "sharePrefix")
    val sharePrefix: String,
    @ColumnInfo(name = "date")
    val date: String
)