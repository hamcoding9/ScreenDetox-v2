package com.hamcoding.screendetox.data.firebase.source

import com.hamcoding.screendetox.BuildConfig
import com.hamcoding.screendetox.data.firebase.entity.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("users.json")
    suspend fun getUsers(): Map<String, User>

    companion object {
        private const val baseUrl = BuildConfig.BASE_URL

        fun create(): ApiClient {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClient::class.java)
        }
    }
}