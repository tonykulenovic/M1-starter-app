package com.cpen321.usermanagement.data.repository

import android.util.Log
import com.cpen321.usermanagement.data.remote.api.BookInterface
import com.cpen321.usermanagement.data.remote.dto.Book
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val bookInterface: BookInterface
) : BookRepository {

    companion object {
        private const val TAG = "BookRepositoryImpl"
    }

    override suspend fun searchBooksByHobbies(hobbies: List<String>): Result<List<Book>> {
        return try {
            if (hobbies.isEmpty()) {
                return Result.success(emptyList())
            }

            // Create search query from hobbies
            val query = hobbies.joinToString(" OR ")
            val response = bookInterface.searchBooks(query = query, limit = 6)

            if (response.isSuccessful && response.body()?.books != null) {
                val books = response.body()?.books?.filter { it.title.isNotBlank() } ?: emptyList()
                Result.success(books)
            } else {
                Log.e(TAG, "Failed to fetch books: ${response.code()}")
                Result.failure(Exception("Failed to fetch books"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching books", e)
            Result.failure(e)
        }
    }
}