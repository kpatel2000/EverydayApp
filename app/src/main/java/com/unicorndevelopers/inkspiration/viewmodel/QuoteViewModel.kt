package com.unicorndevelopers.inkspiration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unicorndevelopers.inkspiration.models.QuoteData
import com.unicorndevelopers.inkspiration.repository.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel(){

    private val quoteRepository = QuoteRepository()
    private val _quote = MutableLiveData<List<QuoteData>>()
    val quoteLiveData: LiveData<List<QuoteData>> = _quote

    private val _image = MutableLiveData<String?>()
    val image: LiveData<String?> = _image

    fun getQuote() = viewModelScope.launch {
        val quote = quoteRepository.getQuote()
        if(quote?.quote != null){
            _quote.value = quote.quote!!
        }
        _image.value = quote?.imageUrl
    }
    fun resetLiveData() {
        _quote.value = emptyList()
        _image.value = null
    }

}