package com.unicorndevelopers.inkspiration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unicorndevelopers.inkspiration.models.Quote
import com.unicorndevelopers.inkspiration.models.QuoteData
import com.unicorndevelopers.inkspiration.repository.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel(){

    private val quoteRepository = QuoteRepository()
    private val _quote = MutableLiveData<Quote?>()
    val quoteLiveData: LiveData<Quote?> = _quote

    fun getQuote() = viewModelScope.launch {
        val quote = quoteRepository.getQuote()
        _quote.value = quote
    }
    fun resetLiveData() {
        _quote.value = null
    }

}