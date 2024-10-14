package com.example.trabajopractico.data.model

import com.example.trabajopractico.data.RetrofitService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient {
    private val baseURL = "https://api.themoviedb.org/3/"

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()



    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }

}