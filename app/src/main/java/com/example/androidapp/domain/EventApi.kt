package com.example.androidapp.domain

import retrofit2.http.*

object EventApi {
    interface Service {
        @GET("/api/item")
        suspend fun find(): EventListDto

        @GET("/api/item/{id}")
        suspend fun read(@Path("id") itemId: Int): Event;

        @Headers("Content-Type: application/json")
        @POST("/api/item")
        suspend fun create(@Body item: Event): Event

        @Headers("Content-Type: application/json")
        @PUT("/api/item/{id}")
        suspend fun update(@Path("id") itemId: Int, @Body item: Event): Event
    }

    val service: Service = MainApi.retrofit.create(Service::class.java)
}