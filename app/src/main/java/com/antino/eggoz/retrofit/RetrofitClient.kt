package com.antino.eggoz.retrofit

import com.antino.eggoz.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {
    private val BASE_URL = Constants.AppUrl
    val okhttp3=OkHttpClient().newBuilder()
        .connectTimeout(50,TimeUnit.SECONDS)
        .readTimeout(50,TimeUnit.SECONDS)
        .writeTimeout(50,TimeUnit.SECONDS)
        .build()



    private var retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).client(okhttp3).build()

    fun getApi(): RetrofitApiService {
        return retrofit.create(RetrofitApiService::class.java)
    }
}