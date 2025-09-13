package com.cpen321.usermanagement.data.repository

import com.cpen321.usermanagement.data.remote.dto.Book

interface BookRepository {
    suspend fun searchBooksByHobbies(hobbies: List<String>): Result<List<Book>>
}