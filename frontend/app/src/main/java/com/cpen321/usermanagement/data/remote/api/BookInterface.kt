package com.cpen321.usermanagement.data.remote.api

import com.cpen321.usermanagement.data.remote.dto.BookSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookInterface {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("limit") limit: Int = 10,
        @Query("fields") fields: String = "key,title,author_name,first_publish_year,cover_i,subject"
    ): Response<BookSearchResponse>
}