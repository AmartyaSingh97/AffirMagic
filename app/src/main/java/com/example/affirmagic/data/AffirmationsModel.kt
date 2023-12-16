package com.example.affirmagic.data

data class AffirmationsModel(
    val text: String,
    val author: String,
    val uniqueId: String,
    val type: String,
    val dzType: String,
    val language: String,
    val bgImageUrl: String,
    val theme: String,
    val themeTitle: String,
    val articleUrl: String,
    val dzImageUrl: String,
    val primaryCTAText: String,
    val sharePrefix: String
)
