package com.unicorndevelopers.inkspiration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unicorndevelopers.inkspiration.models.Quote
import com.unicorndevelopers.inkspiration.models.QuoteData
import com.unicorndevelopers.inkspiration.repository.QuoteRepository
import kotlinx.coroutines.launch
import java.util.LinkedList

class QuoteViewModel : ViewModel(){

    private val quoteRepository = QuoteRepository()
    private val _quote = MutableLiveData<Quote?>()
    val quoteLiveData: LiveData<Quote?> = _quote

    private val _bufferQuote = MutableLiveData<Quote?>()
    val bufferQuoteLiveData: LiveData<Quote?> = _bufferQuote

    fun getQuote(quote: Quote?) = viewModelScope.launch {
        val responseQuote = quoteRepository.getQuote(quote)
        _bufferQuote.value = responseQuote
        val _quoteLiveData = Quote(
            quote = LinkedList<QuoteData>(),
            imageUrl = LinkedList<String?>(),
            networkIssue = false
        )
        _bufferQuote.value?.quote?.forEachIndexed { index, quoteData ->
            if (index != 2) {
                val image = _bufferQuote.value?.imageUrl?.toMutableList()?.get(index)
                _quoteLiveData.quote?.add(quoteData)
                _quoteLiveData.imageUrl?.add(image)
            }
        }
        _quote.value =_quoteLiveData
    }
    fun resetLiveData() {
        _quote.value = null
    }

    fun updateQuote() {
        _bufferQuote.value?.quote?.poll()
        _bufferQuote.value?.imageUrl?.poll()
        val _quoteData = Quote(
            quote = LinkedList<QuoteData>(),
            imageUrl = LinkedList<String?>(),
            networkIssue = false
        )
        _bufferQuote.value?.quote?.forEachIndexed { index, quoteData ->
            val image = _bufferQuote.value?.imageUrl?.toMutableList()?.get(index)
            _quoteData.quote?.add(quoteData)
            _quoteData.imageUrl?.add(image)
        }
        _quote.value = _quoteData
    }

}