package com.example.trabajopractico.data.model

data class PeliculasPopularesApi(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int

)