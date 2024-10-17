package com.example.trabajopractico.data

import com.example.trabajopractico.data.model.PeliculasPopularesApi
import retrofit2.http.GET

interface RetrofitService {
    @GET("discover/movie?sort_by=popularity.desc&language=es-LAM&api_key=cc31ee3ceb6abdf92fed774eb7e300d7")
    suspend fun getPeliculas(): PeliculasPopularesApi
}
