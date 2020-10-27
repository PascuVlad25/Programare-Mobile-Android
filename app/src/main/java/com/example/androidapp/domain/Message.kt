package com.example.androidapp.domain

data class Message(
    val id: Int,
    val fromName: String,
    val subject: String,
    val content: String,
    var read: Boolean
) {
}