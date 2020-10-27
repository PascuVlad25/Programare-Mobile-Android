package com.example.androidapp.domain

import android.util.Log

object MessageRepository {
    private var cachedItems: MutableList<Message>? = null;

    suspend fun loadAll(): List<Message> {
        if (cachedItems != null) {
            return cachedItems as List<Message>;
        }
        cachedItems = mutableListOf()
        val items = MessageApi.service.find()
        cachedItems?.addAll(items)
        return cachedItems as List<Message>
    }

    suspend fun load(itemId: Int): Message {
        val item = cachedItems?.find { it.id == itemId }
        if (item != null) {
            return item
        }
        return MessageApi.service.read(itemId)
    }

    suspend fun save(item: Message): Message {
        val createdItem = MessageApi.service.create(item)
        cachedItems?.add(createdItem)
        return createdItem
    }

    suspend fun update(item: Message): Message? {
        val updatedItem = item.id?.let { MessageApi.service.update(it, item) }
        val index = cachedItems?.indexOfFirst { it.id == item.id }
        if (index != null) {
            if (updatedItem != null) {
                cachedItems?.set(index, updatedItem)
            }
        }
        return updatedItem
    }
}