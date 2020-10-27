package com.example.androidapp.domain

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object MessageApi {
    private const val URL = "http://192.168.0.32:3000/"

    interface Service {
        @GET("/item")
        suspend fun find(): List<Message>

        @GET("/item/{id}")
        suspend fun read(@Path("id") itemId: Int): Message;

        @Headers("Content-Type: application/json")
        @POST("/item")
        suspend fun create(@Body item: Message): Message

        @Headers("Content-Type: application/json")
        @PUT("/item/{id}")
        suspend fun update(@Path("id") itemId: Int, @Body item: Message): Message
    }

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(Service::class.java)
}