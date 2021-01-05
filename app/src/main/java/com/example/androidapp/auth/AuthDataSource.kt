package com.example.androidapp.auth

import com.example.androidapp.domain.MainApi
import com.example.androidapp.domain.Result
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

object AuthDataSource {
    interface AuthService {
        @Headers("Content-Type: application/json")
        @POST("/api/auth/login")
        suspend fun login(@Body user: User): TokenHolder
    }

    private val authService: AuthService = MainApi.retrofit.create(AuthService::class.java)

    suspend fun login(user: User): Result<TokenHolder> {
        try {
            return Result.Success(authService.login(user))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}