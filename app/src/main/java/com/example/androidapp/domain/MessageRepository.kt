package com.example.androidapp.domain

import java.lang.Exception

object MessageRepository {
    private var cachedItems: MutableList<Event>? = null;

    suspend fun loadAll(): List<Event> {
        if (cachedItems != null) {
            System.out.println("Cached items" + cachedItems!!.count());
            return cachedItems as List<Event>;
        }
        try {
            System.out.println("Se incepe loadingul")
            val items = EventApi.service.find()
            System.out.println("S au incarcat" + items.items.count())
            cachedItems = mutableListOf()
            cachedItems?.addAll(items.items)
            return cachedItems as List<Event>
        } catch (e: Exception) {
            System.out.println(e);
            return cachedItems as List<Event>;
        }
    }

    suspend fun load(itemId: Int): Event {
        val item = cachedItems?.find { it.id == itemId }
        if (item != null) {
            return item
        }
        return EventApi.service.read(itemId)
    }

    suspend fun save(item: Event): Event {
        val createdItem = EventApi.service.create(item)
        cachedItems?.add(createdItem)
        return createdItem
    }

    suspend fun update(item: Event): Event? {
        val updatedItem = item.id?.let { EventApi.service.update(it, item) }
        val index = cachedItems?.indexOfFirst { it.id == item.id }
        if (index != null) {
            if (updatedItem != null) {
                cachedItems?.set(index, updatedItem)
            }
        }
        return updatedItem
    }
}