package com.unicorndevelopers.inkspiration.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unicorndevelopers.inkspiration.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(UiStates())
    val uiState: StateFlow<UiStates> = _uiState

    private val quoteRepository = QuoteRepository()

    init {
        getQuotes()
    }
    fun onEvent(event: QuotesUiEvents) {
        when(event) {
            QuotesUiEvents.NextQuote -> {
                if (_uiState.value.quotes.isNotEmpty()) {
                    _uiState.update {
                        it.copy(quotes = it.quotes.drop(1), images = it.images.drop(1))
                    }
                    if(_uiState.value.quotes.size < 3) {
                        prefetchQuote()
                    }
                }
            }

            QuotesUiEvents.TryAgain -> {
                getQuotes()
            }

            QuotesUiEvents.ShareQuote -> TODO()
        }
    }

    private fun getQuotes() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val quoteResponse = quoteRepository.getQuote(limit = 3)
            val imageResponse = quoteRepository.getCategoryImage("nature", 3)
            if(!quoteResponse.isNullOrEmpty() && imageResponse.isNotEmpty()) {
                _uiState.update {
                    it.copy(isLoading = false, quotes = quoteResponse, images = imageResponse, isNetworkError = false)
                }
            } else if(quoteResponse != null && quoteResponse.isEmpty()) {
                _uiState.update { it.copy(isNetworkError = true) }
            }
        }
    }

    private fun prefetchQuote() {
        viewModelScope.launch {
            val quotesList = _uiState.value.quotes as ArrayList<String>
            val imagesList = _uiState.value.images as ArrayList<String?>
            val quoteResponse = quoteRepository.prefetchQuote()
            val imageResponse = quoteRepository.prefetchCategoryImage()
            if(!quoteResponse.isNullOrEmpty() && imageResponse != null) {
                quotesList.add(quoteResponse)
                imagesList.add(imageResponse)
                val updatedQuoteList: List<String> = quotesList
                val updatedImageList: List<String?> = imagesList
                _uiState.update {
                    it.copy(quotes = updatedQuoteList, images = updatedImageList)
                }
            } else if(quoteResponse != null && quoteResponse.isEmpty()) {
                _uiState.update { it.copy(isNetworkError = true) }
            }
        }
    }
}

data class UiStates(
    val quotes: List<String> = emptyList(),
    val images: List<String?> = emptyList(),
    val isLoading: Boolean = false,
    val isNetworkError: Boolean = false
)

sealed interface QuotesUiEvents {
    data object NextQuote : QuotesUiEvents
    data object TryAgain : QuotesUiEvents
    data object ShareQuote : QuotesUiEvents
}