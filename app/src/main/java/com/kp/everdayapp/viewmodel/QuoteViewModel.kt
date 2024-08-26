package com.kp.everdayapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kp.everdayapp.models.QuoteData
import com.kp.everdayapp.repository.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel(){

    private val quoteRepository = QuoteRepository()
    private val _quote = MutableLiveData<QuoteData>()
    val quoteLiveData: LiveData<QuoteData> = _quote

    fun getQuote() = viewModelScope.launch {
        val quote = quoteRepository.getQuote()
        _quote.value = quote!!
    }

}