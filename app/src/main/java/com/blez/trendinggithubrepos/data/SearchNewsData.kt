package com.blez.trendinggithubrepos.data class SearchNewsData(
    val articles: List<S_Article>,
    val status: String,
    val totalResults: Int
)

data class S_Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: S_Source,
    val title: String,
    val url: String,
    val urlToImage: String
)

data class S_Source(
    val id: String,
    val name: String
)