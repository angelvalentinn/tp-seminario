package com.example.trabajopractico.data

import com.example.trabajopractico.Actor
import com.example.trabajopractico.Credits
import com.example.trabajopractico.PeliculaDetalle
import com.example.trabajopractico.data.model.PeliculasPopularesApi
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("discover/movie?sort_by=popularity.desc&language=es-LAM&api_key=cc31ee3ceb6abdf92fed774eb7e300d7")
    suspend fun getPeliculas(): PeliculasPopularesApi

    @GET("https://api.themoviedb.org/3/movie/{id}/credits?api_key=cc31ee3ceb6abdf92fed774eb7e300d7")
    suspend fun getActores(@Path("id") id: String): Credits

    @GET("https://api.themoviedb.org/3/movie/{id}?append_to_response=videos&include_adult=none&include_video=true&language=es-LAM&sort_by=popularity.desc&api_key=cc31ee3ceb6abdf92fed774eb7e300d7")
    suspend fun getDetalle(@Path("id") id: String): PeliculaDetalle
}
