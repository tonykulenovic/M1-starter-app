package com.cpen321.usermanagement.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpen321.usermanagement.data.remote.dto.Book
import com.cpen321.usermanagement.data.remote.dto.User
import com.cpen321.usermanagement.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val successMessage: String? = null,
    val isLoadingBooks: Boolean = false,
    val books: List<Book> = emptyList(),
    val userHobbies: List<String> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun setSuccessMessage(message: String) {
        _uiState.value = _uiState.value.copy(successMessage = message)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }

    fun loadBooksForUser(user: User) {
        val hobbies = user.hobbies
        if (hobbies != _uiState.value.userHobbies) {
            _uiState.value = _uiState.value.copy(userHobbies = hobbies)
            fetchBooks(hobbies)
        }
    }

    private fun fetchBooks(hobbies: List<String>) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoadingBooks = true,
                errorMessage = null
            )

            val result = bookRepository.searchBooksByHobbies(hobbies)
            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    isLoadingBooks = false,
                    books = result.getOrNull() ?: emptyList()
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoadingBooks = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to load books"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}