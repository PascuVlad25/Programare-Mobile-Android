package com.example.androidapp.viewMessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapp.domain.Message
import com.example.androidapp.domain.MessageRepository
import kotlinx.coroutines.launch

class ViewMessageViewModel : ViewModel() {
    private val mutableItem = MutableLiveData<Message>().apply { value = Message(-1, "", "", "", false) }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val item: LiveData<Message> = mutableItem
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadItem(itemId: Int) {
        viewModelScope.launch {
            mutableFetching.value = true
            mutableException.value = null
            try {
                mutableItem.value = MessageRepository.load(itemId)
                mutableFetching.value = false
            } catch (e: Exception) {
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun updateItem(item: Message) {
        viewModelScope.launch {
            MessageRepository.update(item)
        }
//            val item = mutableItem.value ?: return@launch
//            item.read = true
//            mutableFetching.value = true
//            mutableException.value = null
//            System.out.println(item)
//            try {
//                if (item.id > -1) {
//                    mutableItem.value = MessageRepository.update(item)
//                } else {
//                    mutableItem.value = MessageRepository.save(item)
//                }
//                mutableCompleted.value = true
//                mutableFetching.value = false
//            } catch (e: Exception) {
//                mutableException.value = e
//                mutableFetching.value = false
//            }
//        }
    }
}