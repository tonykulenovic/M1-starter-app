package com.cpen321.usermanagement.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookSearchResponse(
    @SerializedName("docs")
    val books: List<Book>?,
    @SerializedName("numFound")
    val totalFound: Int = 0
)

data class Book(
    @SerializedName("key")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("author_name")
    val authors: List<String>? = null,
    @SerializedName("first_publish_year")
    val publishYear: Int? = null,
    @SerializedName("cover_i")
    val coverId: Int? = null,
    @SerializedName("subject")
    val subjects: List<String>? = null
) {
    val authorNames: String
        get() = authors?.joinToString(", ") ?: "Unknown Author"

    val coverUrl: String?
        get() = coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
}
