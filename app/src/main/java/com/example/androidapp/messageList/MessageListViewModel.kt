package com.example.androidapp.messageList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.domain.Message
import com.example.androidapp.domain.MessageRepository
import kotlinx.coroutines.launch

class MessageListViewModel : ViewModel() {
    private val mutableItems = MutableLiveData<List<Message>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val items: LiveData<List<Message>> = mutableItems
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    fun createItem(id: Int): Unit {
        val list = mutableListOf<Message>()
        list.addAll(mutableItems.value!!)
        list.add(Message(id, "fromName", "Subject", "Content", false))
        mutableItems.value = list
    }

    fun loadItems() {
        viewModelScope.launch {
            mutableLoading.value = true
            mutableException.value = null
            try {
                mutableItems.value = MessageRepository.loadAll()
                mutableLoading.value = false
            } catch (e: Exception) {
                mutableException.value = e
                mutableLoading.value = false
            }
        }
    }
}